package com.phoenix.huashi.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.huashi.common.*;
import com.phoenix.huashi.controller.request.*;
import com.phoenix.huashi.controller.response.GetUserResponse;
import com.phoenix.huashi.dto.WxAccessToken;
import com.phoenix.huashi.dto.WxErrCode;
import com.phoenix.huashi.dto.member.Experience;
import com.phoenix.huashi.dto.recruitproject.BriefProjectInformation;
import com.phoenix.huashi.dto.user.BriefUserName;
import com.phoenix.huashi.config.YmlConfig;
import com.phoenix.huashi.dto.SessionData;
import com.phoenix.huashi.dto.WxSession;
import com.phoenix.huashi.entity.DisplayProject;
import com.phoenix.huashi.entity.Member;
import com.phoenix.huashi.entity.RecruitProject;
import com.phoenix.huashi.entity.User;
import com.phoenix.huashi.enums.MemberTypeEnum;
import com.phoenix.huashi.mapper.MemberMapper;
import com.phoenix.huashi.mapper.RecruitProjectMapper;
import com.phoenix.huashi.mapper.UserMapper;
import com.phoenix.huashi.service.UserService;
import com.phoenix.huashi.util.*;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import static com.phoenix.huashi.common.CommonConstants.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private YmlConfig ymlConfig;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private RecruitProjectMapper recruitProjectMapper;

    @Autowired
    private TransferManager transferManager;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private COSClient cosClient;

    @Autowired
    private JavaMailSender jms;

    @Value("${spring.mail.username}")
    private String sender;


    @Value("${mini-app.app-id}")
    private String appId;

    @Value("${mini-app.app-secret}")
    private String appSecret;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private PasswordUtil passwordUtil;

    @Override
    public User getUserByChuangNum(String userChuangNum) {
        User user = userMapper.getUserByChuangNum(userChuangNum);
        return user;
    }


    @Override
    public Page<BriefProjectInformation> getBriefTeamList(GetTeamListRequest request, String userChuangNum) {
        List<Long> projectIdList = new ArrayList<>();
        if (request.getTeamType().equals(0))
            projectIdList = memberMapper.getTeamByChuangNumAndMemberType(userChuangNum, MemberTypeEnum.MEMBER.getDescription());
        else if (request.getTeamType().equals(1))
            projectIdList = memberMapper.getTeamByChuangNumAndMemberType(userChuangNum, MemberTypeEnum.CAPTAIN.getDescription());
        else if (request.getTeamType().equals(2))
            projectIdList = memberMapper.getTeamByChuangNum(userChuangNum);
        PageParam pageParam = request.getPageParam();
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize(), pageParam.getOrderBy());
        List<BriefProjectInformation> briefProjectInformationList = new LinkedList<>();
        for (Long projectId : projectIdList) {
            RecruitProject recruitProject = recruitProjectMapper.getRecruitProjectById(projectId);
            if (recruitProject != null) {
                BriefProjectInformation briefProjectInformation = new BriefProjectInformation(recruitProject.getId(), recruitProject.getName(), recruitProject.getTag1(), recruitProject.getTag2(), recruitProject.getTag3(), recruitProject.getCaptainName(), userMapper.getUserByChuangNum(recruitProject.getCaptainChuangNum()).getDepartment(), recruitProject.getStatus());
                briefProjectInformationList.add(briefProjectInformation);
            }
        }
        return new Page(new PageInfo<>(briefProjectInformationList));
    }

    @Override
    public void updateUserByChuangNum(UpdateUserByChuangNumRequest updateUserByChuangNumRequest, String userChuangNum) {
        userMapper.updateUserByChuangNum(updateUserByChuangNumRequest.getNickname(), updateUserByChuangNumRequest.getPortrait(), updateUserByChuangNumRequest.getTelephone(), updateUserByChuangNumRequest.getDepartment(), updateUserByChuangNumRequest.getMajor(), updateUserByChuangNumRequest.getGrade(), updateUserByChuangNumRequest.getQQ(), updateUserByChuangNumRequest.getWechatNum(), updateUserByChuangNumRequest.getResume(), updateUserByChuangNumRequest.getAttachment(), userChuangNum);
    }

    @Override
    public void fillUserInformation(FillUserInformationRequest fillUserInformationRequest, String userChuangNum) {
        User user = userMapper.getUserByChuangNum(userChuangNum);
        userMapper.fillUserInformation(userChuangNum, fillUserInformationRequest.getNickname(), fillUserInformationRequest.getName(), fillUserInformationRequest.getGender(), fillUserInformationRequest.getSchool(), fillUserInformationRequest.getDepartment(), fillUserInformationRequest.getMajor(), fillUserInformationRequest.getGrade(), fillUserInformationRequest.getTelephone(), fillUserInformationRequest.getQQ(), fillUserInformationRequest.getWechatNum(), fillUserInformationRequest.getPortrait(), fillUserInformationRequest.getResume(), fillUserInformationRequest.getAttachment(), fillUserInformationRequest.getStudentNumber());

    }

    @Override
    public Page<BriefUserName> searchBriefUserNameListByName(GetBriefUserNameListRequest searchBriefUserNameListRequest) {
        PageParam pageParam = searchBriefUserNameListRequest.getPageParam();
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize(), pageParam.getOrderBy());
        List<BriefUserName> briefUserNameList = userMapper.searchBriefUserNameListByName(searchBriefUserNameListRequest.getName());
        return new Page(new PageInfo<>(briefUserNameList));
    }


    @Override
    public SessionData login(String code) {

        //shadow test
        if (CommonConstants.SHADOW_TEST.equals(code)) {
            sessionUtils.setSessionId(CommonConstants.SHADOW_TEST);
            return new SessionData();
        }

        WxSession wxSession = Optional.ofNullable(
                        getWxSessionByCode(code))
                .orElse(new WxSession());

        checkWxSession(wxSession);

        String sessionId = sessionUtils.generateSessionId();

        User user = userMapper.getUserByOpenId(wxSession.getOpenId());

        if (user != null) {
            sessionUtils.setSessionId(user.getSessionId());
            return new SessionData(user);
        }

        User user1 = new User(sessionId, wxSession.getOpenId(), wxSession.getUnionId(), wxSession.getSessionKey(), TimeUtil.getCurrentTimestamp(), "??????????????????");

//        User user1 = new User();
        userMapper.newUser(user1);

        long userId = user1.getId();

        userMapper.updateChuangNum("hs" + String.format("%08d", userId), userId);

        user1.setChuangNum("hs" + String.format("%08d", userId));
        user1.setId(userId);

        return new SessionData(user1);
    }

    @Override
    public  SessionData webSignUp(String email, String password, int type){

        if (userMapper.getUserByEmail(email) != null) throw new CommonException(CommonErrorCode.EMAIL_HAS_BEEN_USED);
        if (!passwordUtil.EvalPWD(password)) throw new CommonException(CommonErrorCode.PASSWORD_NOT_QUALIFIED);

        String sessionId = sessionUtils.generateSessionId();
        User user = User.builder()
                .createTime(TimeUtil.getCurrentTimestamp())
                .email(email)
                .password(passwordUtil.convert(password))
                .nickname("??????????????????")
                .sessionId(sessionId)
                .build();

        if (!email.endsWith("ecnu.edu.cn")) throw new CommonException(CommonErrorCode.NOT_SCHOOL_EMAIL);
        if (type == 0) user.setType(0);
        else if (email.contains("stu")) throw new CommonException(CommonErrorCode.NOT_TEACHER_EMAIL);
        else user.setType(1);

        userMapper.newUser(user);
        user.setChuangNum("hs" + String.format("%08d", user.getId()));
        redisUtils.set(sessionId, new SessionData(user), 86400);
        userMapper.updateChuangNum(user.getChuangNum(), user.getId());
        return new SessionData(user);

    }

    @Override
    public SessionData webLogin(String emailOrChuangNum, String password) {
        String sessionId = sessionUtils.generateSessionId();

        User user = userMapper.getUserByEmailOrChuangNUm(emailOrChuangNum);

        AssertUtil.notNull(user, CommonErrorCode.LOGIN_FAILED);

        AssertUtil.isTrue(passwordUtil.convert(password).equals(user.getPassword()), CommonErrorCode.LOGIN_FAILED);

        sessionUtils.setSessionId(sessionId);

        redisUtils.set(sessionId, new SessionData(user), 86400);

        return new SessionData(user);
    }




    @Override
    public String adminLogin(String number, String password) {

        String sessionId = null;
        AssertUtil.isTrue((number.equals(CommonConstants.ADMIN1_NUMBER)&&password.equals(CommonConstants.ADMIN1_PASSWORD))
                |(number.equals(CommonConstants.ADMIN2_NUMBER)&&password.equals(CommonConstants.ADMIN2_PASSWORD))
                |(number.equals(CommonConstants.ADMIN3_NUMBER)&&password.equals(CommonConstants.ADMIN3_PASSWORD)), CommonErrorCode.LOGIN_FAILED);

        if(number.equals(CommonConstants.ADMIN1_NUMBER)&&password.equals(CommonConstants.ADMIN1_PASSWORD)){
            sessionId=CommonConstants.ADMIN1_SESSIONID;
        }
        else if(number.equals(CommonConstants.ADMIN2_NUMBER)&&password.equals(CommonConstants.ADMIN2_PASSWORD)){
            sessionId=CommonConstants.ADMIN2_SESSIONID;
        }
        else if(number.equals(CommonConstants.ADMIN3_NUMBER)&&password.equals(CommonConstants.ADMIN3_PASSWORD)){
            sessionId=CommonConstants.ADMIN3_SESSIONID;
        }
        sessionUtils.setSessionId(sessionId);

        return sessionId;
    }


    private WxSession getWxSessionByCode(String code) {
        Map<String, String> requestUrlParam = new HashMap<>();
        //?????????appId
        requestUrlParam.put("appid", ymlConfig.getAppId());
//        requestUrlParam.put("appid", "wx22fa1182d4e66c4a");
        //?????????secret
        requestUrlParam.put("secret", ymlConfig.getAppSecret());
//        requestUrlParam.put("secret", "200e82982f7ec2a2812fc3ae9f2d5f15");
        //?????????????????????code
        requestUrlParam.put("js_code", code);
        //???????????????????????????
        requestUrlParam.put("grant_type", "authorization_code");
        //??????post????????????????????????????????????openid??????????????????
        String result = HttpUtil.get(CommonConstants.WX_SESSION_REQUEST_URL, requestUrlParam);
//        String result = HttpUtil.get("https://api.weixin.qq.com/sns/jscode2session", requestUrlParam);

        return JsonUtil.toObject(result, WxSession.class);
    }

    private void checkWxSession(WxSession wxSession) {
        if (wxSession.getErrcode() != null) {
            AssertUtil.isFalse(-1 == wxSession.getErrcode(), CommonErrorCode.WX_LOGIN_BUSY, wxSession.getErrmsg());
            AssertUtil.isFalse(40029 == wxSession.getErrcode(), CommonErrorCode.WX_LOGIN_INVALID_CODE, wxSession.getErrmsg());
            AssertUtil.isFalse(45011 == wxSession.getErrcode(), CommonErrorCode.WX_LOGIN_FREQUENCY_REFUSED, wxSession.getErrmsg());
            AssertUtil.isTrue(wxSession.getErrcode() == 0, CommonErrorCode.WX_LOGIN_UNKNOWN_ERROR, wxSession.getErrmsg());
        }
    }

    @Override
    public String resumeUpload(String userChuangNum, MultipartFile file, String fileName) {
        User user = userMapper.getUserByChuangNum(userChuangNum);
        if (user.getAttachment() != null && user.getAttachment2() != null && user.getAttachment3() != null)
            throw new CommonException(CommonErrorCode.EXCEED_MAX_NUMBER);
        if (fileName .equals(user.getAttachmentName())  || fileName .equals(user.getAttachment2Name())  || fileName .equals(user.getAttachment3Name()))
            throw new CommonException(CommonErrorCode.WRONG_FILE_NAME);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        UploadResult uploadResult = null;
        String res = null;

        try {

            String name = file.getOriginalFilename();
//            String name=new String(file.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
            AssertUtil.notNull(name, CommonErrorCode.FILENAME_CAN_NOT_BE_NULL);
            String first = name.substring(0, name.lastIndexOf("."));
            String extension = name.substring(name.lastIndexOf("."));


            PutObjectRequest putObjectRequest = new PutObjectRequest(COS_BUCKET_NAME, user.getChuangNum() + "." + name, file.getInputStream(), objectMetadata);

            // ???????????????????????????????????????Upload
            // ?????????????????? waitForUploadResult ???????????????????????????????????????UploadResult, ??????????????????
            Upload upload = transferManager.upload(putObjectRequest);
            uploadResult = upload.waitForUploadResult();

            res = cosClient.getObjectUrl(COS_BUCKET_NAME, user.getChuangNum() + "." + name).toString();
            if (user.getAttachment() == null) {
                user.setAttachment(res);
                user.setAttachmentName(fileName);
            } else if (user.getAttachment2() == null) {
                user.setAttachment2(res);
                user.setAttachment2Name(fileName);
            } else if (user.getAttachment3() == null) {
                user.setAttachment3(res);
                user.setAttachment3Name(fileName);
            }
            userMapper.updateByPrimaryKey(user);

        } catch (Exception e) {
            //e.printStackTrace();
            throw new CommonException(CommonErrorCode.UPLOAD_FILE_FAIL);
        }


        // ??????????????????????????? transferManager ????????????????????????
        // ??????????????????????????????????????? -> ?????? TransferManager

        return res;
    }


    @Override
    public List<Experience> getUserProjectExperience(String userChuangNum) {
        return memberMapper.getMemberExperienceInRecruitProject(userChuangNum, -1);
    }

    @Override
    public String resumeDelete(String url, String chuangNum) {
        User user = userMapper.selectOne(User.builder().chuangNum(chuangNum).build());
        if (user.getAttachment() == url) user.setAttachment(null);
        else if (user.getAttachment2() == url) user.setAttachment2(null);
        else if (user.getAttachment3() == url) user.setAttachment3(null);
        else throw new CommonException(CommonErrorCode.FILE_NOT_EXIST);
        userMapper.updateByPrimaryKey(user);
        cosClient.deleteObject(COS_BUCKET_NAME, url.substring(url.indexOf(chuangNum)));
        return "????????????";
    }

    @Override
    public String uploadResumeRTF(String userChuangNum,MultipartFile file) {
        User user = userMapper.getUserByChuangNum(userChuangNum);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        UploadResult uploadResult = null;
        String res = null;

        try {

            String name = file.getOriginalFilename();
//            String name=new String(file.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
            AssertUtil.notNull(name, CommonErrorCode.FILENAME_CAN_NOT_BE_NULL);
            String first = name.substring(0, name.lastIndexOf("."));
            String extension = name.substring(name.lastIndexOf("."));

            String time = TimeUtil.getCurrentTimestamp();
            PutObjectRequest putObjectRequest = new PutObjectRequest(COS_BUCKET_NAME, "resumeRTF"+time+userChuangNum + "." + name, file.getInputStream(), objectMetadata);

            // ???????????????????????????????????????Upload
            // ?????????????????? waitForUploadResult ???????????????????????????????????????UploadResult, ??????????????????
            Upload upload = transferManager.upload(putObjectRequest);
            uploadResult = upload.waitForUploadResult();

            res = cosClient.getObjectUrl(COS_BUCKET_NAME, "resumeRTF"+time+userChuangNum + "." + name).toString();
            user.setResume(res);
            userMapper.updateByPrimaryKey(user);

        } catch (Exception e) {
            //e.printStackTrace();
            throw new CommonException(CommonErrorCode.UPLOAD_FILE_FAIL);
        }


        // ??????????????????????????? transferManager ????????????????????????
        // ??????????????????????????????????????? -> ?????? TransferManager

        return res;

    }
    @Override
    public String uploadPortrait(String userChuangNum, MultipartFile file) throws CommonException{
        User user = userMapper.getUserByChuangNum(userChuangNum);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        UploadResult uploadResult = null;
        String res = null;
        String portrait = user.getPortrait();

        try{
            if(user.getPortrait()!=null){
                cosClient.deleteObject(COS_BUCKET_NAME,portrait.substring(portrait.indexOf("portrait"+user.getChuangNum())));
            }
        }catch (Exception e){
            throw new CommonException(CommonErrorCode.UPLOAD_FILE_FAIL);
        }

        try {

            String name = file.getOriginalFilename();
//            String name=new String(file.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
            AssertUtil.notNull(name, CommonErrorCode.FILENAME_CAN_NOT_BE_NULL);
            String first = name.substring(0, name.lastIndexOf("."));
            String extension = name.substring(name.lastIndexOf("."));

            if(!extension.equals(".bmp") && !extension.equals(".jpg") && !extension.equals(".png")){
                throw new CommonException(CommonErrorCode.WRONG_FILE_FORMAT);
            }
            PutObjectRequest putObjectRequest = new PutObjectRequest(COS_BUCKET_NAME, "portrait"+user.getChuangNum() + "." + name, file.getInputStream(), objectMetadata);

            // ???????????????????????????????????????Upload
            // ?????????????????? waitForUploadResult ???????????????????????????????????????UploadResult, ??????????????????
            Upload upload = transferManager.upload(putObjectRequest);
            uploadResult = upload.waitForUploadResult();

            res = cosClient.getObjectUrl(COS_BUCKET_NAME, "portrait"+user.getChuangNum() + "." + name).toString();
            user.setPortrait(res);
            userMapper.updateByPrimaryKey(user);

        } catch (Exception e) {
            //e.printStackTrace();
            throw new CommonException(CommonErrorCode.UPLOAD_FILE_FAIL);
        }


        // ??????????????????????????? transferManager ????????????????????????
        // ??????????????????????????????????????? -> ?????? TransferManager

        return res;
    }


    @Override
    public void updateResumeRTF(String userChuangNum, String resume) {
        User user = userMapper.getUserByChuangNum(userChuangNum);
        user.setResume(resume);
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public void checkCode(String email, String code) throws CommonException {
        //if (!redisUtils.hasKey(email)) throw new CommonException((CommonErrorCode.HAS_NOT_SENT_EMAIL));
        if (redisUtils.isExpire(email)) throw new CommonException(CommonErrorCode.VERIFICATION_CODE_HAS_EXPIRED);
        if (!redisUtils.get(email).equals(code)) throw new CommonException(CommonErrorCode.VERIFICATION_CODE_WRONG);
        else redisUtils.del(email);
    }


    @Override
    public String sendEmail(String email, int flag) {

        String verificationCode = RandomVerifyCodeUtil.getRandomVerifyCode();
        redisUtils.set(email, verificationCode, 3000);
        try {
            messageUtil.sendMail(sender, email, verificationCode, jms, flag);
        } catch (Exception e) {
            throw new CommonException(CommonErrorCode.SEND_EMAIL_FAILED);
        }
        return verificationCode;
    }

    @Override
    public String getQRCode() throws IOException {
        Map<String, String> requestUrlParam = new HashMap<>();
        requestUrlParam.put("grant_type","client_credential");
        requestUrlParam.put("appid",appId);
        requestUrlParam.put("secret",appSecret);
        String result = null;

        result = HttpUtil.get(WX_ACCESS_TOKEN_REQUEST_URL,requestUrlParam);

        System.out.println(result);
        WxAccessToken wxAccessToken = JsonUtil.toObject(result, WxAccessToken.class);

        assert wxAccessToken != null;
        checkWxAccessToken(wxAccessToken);

        String requestURL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + wxAccessToken.getAccess_token();

//        return QRCodeResponse.builder()
//                .token(UUID.randomUUID().toString().substring(0,31))
//                .url(requestURL)
//                .build();

//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("scene",);
//        requestBody.put("page",);
//        requestBody.put("env_version","develop");

        JSONObject createQrParam = new JSONObject();
        createQrParam.put("scene", UUID.randomUUID().toString().substring(0,31));
        createQrParam.put("page", "pages/account/account");

//        CloseableHttpResponse response = HttpUtil.post("https://api.weixin.qq.com/wxa/getwxacodeunlimit",requestUrlParam,requestBody);

        PrintWriter out = null;
        InputStream in = null;
        String base64Code = null;
        //????????????????????????
        byte[] data = null;
        try {
            URL realUrl = new URL(requestURL);
            // ?????????URL???????????????
            URLConnection conn = realUrl.openConnection();
            // ???????????????????????????
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ??????POST??????????????????????????????
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // ??????URLConnection????????????????????????
            out = new PrintWriter(conn.getOutputStream());
            // ??????????????????,??????connection??????????????????????????????connection???????????????????????????????????????????????????connection?????????connection?????????????????????URL???????????????
            out.print(createQrParam);
            // flush??????????????????
            out.flush();
            //??????URL???connection???????????????????????????????????????????????????url???????????????,??????????????????????????????????????????????????????????????????????????????????????????
            in = conn.getInputStream();
            // ????????????????????????
            try {
                //?????????????????????????????????????????????????????????????????????
                ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
                byte[] buff = new byte[1000000];
                int rc = 0;
                while ((rc = in.read(buff,0,100)) > 0) {
                    //??????????????????????????????????????????????????????
                    swapStream.write(buff,0,rc);
                }
                //??????connection?????????????????????????????????????????????????????????????????????????????????????????????????????????swapStream???
                data = swapStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            base64Code = new String(Objects.requireNonNull(Base64.getEncoder().encode(data)));
            //Base64???byte[]??????
            System.out.println(base64Code);
        } catch (Exception e) {
            System.out.println("?????? POST ?????????????????????" + e);
            e.printStackTrace();
        }

        // ??????finally?????????????????????????????????
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return base64Code;
    }

    private void checkWxAccessToken(WxAccessToken wxAccessToken) {
        if(wxAccessToken.getErrcode() != null) {
            AssertUtil.isFalse(-1 == wxAccessToken.getErrcode(), CommonErrorCode.WX_LOGIN_BUSY,wxAccessToken.getErrmsg());
            AssertUtil.isFalse(40001 == wxAccessToken.getErrcode(), CommonErrorCode.WX_APPSECRET_INVALID,wxAccessToken.getErrmsg());
            AssertUtil.isFalse(40002 == wxAccessToken.getErrcode(), CommonErrorCode.WX_GRANTTYPE_MUSTBE_CLIENTCREDENTIAL,wxAccessToken.getErrmsg());
            AssertUtil.isFalse(40013 == wxAccessToken.getErrcode(), CommonErrorCode.WX_APPID_INVALID,wxAccessToken.getErrmsg());
        }
    }

    private void checkWxErrCode(WxErrCode wxErrCode) {
        if(wxErrCode.getErrcode() != null) {
            AssertUtil.isFalse(-1 == wxErrCode.getErrcode(), CommonErrorCode.WX_LOGIN_BUSY,wxErrCode.getErrmsg());
            AssertUtil.isFalse(45063 == wxErrCode.getErrcode(), CommonErrorCode.WX_QRCODE_UNAUTHORIZED,wxErrCode.getErrmsg());
            AssertUtil.isFalse(45009 == wxErrCode.getErrcode(), CommonErrorCode.WX_QRCODE_TOO_FREQUENT,wxErrCode.getErrmsg());
        }
    }
}