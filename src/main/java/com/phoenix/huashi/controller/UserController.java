package com.phoenix.huashi.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.phoenix.huashi.annotation.Auth;
import com.phoenix.huashi.common.CommonErrorCode;
import com.phoenix.huashi.common.CommonException;
import com.phoenix.huashi.common.Result;
import com.phoenix.huashi.controller.request.*;
import com.phoenix.huashi.controller.response.GetUserResponse;
import com.phoenix.huashi.dto.SessionData;
import com.phoenix.huashi.dto.recruitproject.BriefProjectInformation;
import com.phoenix.huashi.dto.user.BriefUserName;
import com.phoenix.huashi.entity.User;
import com.phoenix.huashi.service.MessageService;
import com.phoenix.huashi.service.UserService;
import com.phoenix.huashi.util.SessionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@Api("用户相关操作")
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionUtils sessionUtils;


    @GetMapping("/login/{code}")
    @ApiOperation(value = "登录", response = SessionData.class)
    @ApiImplicitParam(name = "code", value = "code", required = true, paramType = "path")
    public Object login(@NotBlank @PathVariable("code") String code) {

        return userService.login(code);

    }

    @Auth
    @GetMapping("/info")
    @ApiOperation(value = "查看用户信息", response = GetUserResponse.class)
    @ApiImplicitParam(name="userChuangNum",value="用户创赛号",required = true,paramType = "query",dataType = "String")
    public Object getUserByChuangNum(@NotNull @RequestParam("userChuangNum") String userChuangNum) {
        User user = userService.getUserByChuangNum(userChuangNum);
        return user;
    }


    @Auth
    @PostMapping("/update")
    @ApiOperation(value = "更新当前用户信息", response = String.class)
    public Object updateUserById(@NotNull @Valid @RequestBody UpdateUserByChuangNumRequest updateUserByChuangNumRequest) {
        String userChuangNum = sessionUtils.getUserChuangNum();
        userService.updateUserByChuangNum(updateUserByChuangNumRequest, userChuangNum);
        return "操作成功";
    }

    @Auth
    @PostMapping("/fill")
    @ApiOperation(value = "填写用户信息", response = String.class)
    public Object fillUserInformation(@NotNull @Valid @RequestBody FillUserInformationRequest fillUserInformationRequest) {
        String userChuangNum = sessionUtils.getUserChuangNum();
        userService.fillUserInformation(fillUserInformationRequest, userChuangNum);
        return "操作成功";
    }

    @Auth
    @PostMapping("/userNameList")
    @ApiOperation(value = "根据姓名获取用户姓名创赛号列表", response = BriefUserName.class)
    public Object getBriefUserNameListByName(@NotNull @Valid @RequestBody GetBriefUserNameListRequest request) {
        return userService.searchBriefUserNameListByName(request);
    }

    @Auth
    @PostMapping("/team")
    @ApiOperation(value = "查看我的组队", response = BriefProjectInformation.class)
    public Object getBriefTeamList(@NotNull @Valid @RequestBody GetTeamListRequest request) {
        String userChuangNum = sessionUtils.getUserChuangNum();
        return userService.getBriefTeamList(request, userChuangNum);
    }

    @Auth
    @PostMapping("/logout")
    @ApiOperation(value = "登出",response = String.class)
    public Object logout(){
        String userChuangNum = sessionUtils.getUserChuangNum();
        sessionUtils.invalidate();
        return userChuangNum;
    }


    @GetMapping("/check")
    @ApiOperation(value = "检查登录态")
    public Object checkSession(){
        if(sessionUtils.getSessionData()==null) return "登录失效";
        return "已登录";
    }

    @Auth
    @PostMapping(value = "/resumeUpload", produces = "application/json")
    @ApiOperation(value = "上传个人简历")
    public Object resumeUpload(MultipartFile file) {
        try {
            return Result.success(userService.resumeUpload(sessionUtils.getUserChuangNum(),file));
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

    @GetMapping(value = "/experience", produces = "application/json")
    @ApiOperation(value = "获取用户项目经历")
    @ApiImplicitParam(name="userChuangNum",value="用户创赛号",required = true,paramType = "query",dataType = "String")
    public Object getUserProjectExperience(@NotNull @RequestParam("userChuangNum")String userChuangNum) {
        try {
            return Result.success(userService.getUserProjectExperience(userChuangNum));
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

    @GetMapping(value = "/downloadResume/{flag}",produces = "application/json")
    @ApiOperation(value = "下载简历附件（pdf或markdown）,整个链接upload接口曾经给过")
    public Result downloadNote(@PathVariable String flag, HttpServletResponse response){
        OutputStream os;
        String basePath = System.getProperty("user.dir") + "/src/main/resources/files";
        List<String> fileNames = FileUtil.listFileNames(basePath);
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
        if(fileName.equals("")) return Result.result(CommonErrorCode.FILE_NOT_EXIST);
        try{
            if(StrUtil.isNotEmpty(fileName)){
                response.addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath + fileName);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            return Result.result(CommonErrorCode.DOWNLOAD_FILE_FAILED);
        }
        return Result.success("下载成功");
    }

}
