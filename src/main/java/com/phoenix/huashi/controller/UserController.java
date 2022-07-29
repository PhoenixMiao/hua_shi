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
    public Result login(@NotBlank @PathVariable("code") String code) {
        try {
            return  Result.success(userService.login(code));
        }catch (CommonException e){
            return  Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @GetMapping("/info")
    @ApiOperation(value = "查看用户信息", response = GetUserResponse.class)
    @ApiImplicitParam(name="userChuangNum",value="用户创赛号",required = true,paramType = "query",dataType = "String")
    public Object getUserByChuangNum(@NotNull @RequestParam("userChuangNum") String userChuangNum) {
        try {
            return  Result.success(userService.getUserByChuangNum(userChuangNum));
        }catch (CommonException e){
            return  Result.result(e.getCommonErrorCode());
        }
    }


    @Auth
    @PostMapping("/update")
    @ApiOperation(value = "更新当前用户信息", response = String.class)
    public Object updateUserById(@NotNull @Valid @RequestBody UpdateUserByChuangNumRequest updateUserByChuangNumRequest) {
        try {
            userService.updateUserByChuangNum(updateUserByChuangNumRequest,sessionUtils.getUserChuangNum());
            return  Result.success("更新成功");
        }catch (CommonException e){
            return  Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @PostMapping("/fill")
    @ApiOperation(value = "填写用户信息", response = String.class)
    public Result fillUserInformation(@NotNull @Valid @RequestBody FillUserInformationRequest fillUserInformationRequest) {
        try {
            String userChuangNum = sessionUtils.getUserChuangNum();
            userService.fillUserInformation(fillUserInformationRequest, userChuangNum);
            return Result.success("更新成功");
        }catch (CommonException e){
            return Result.result(e.getCommonErrorCode());
        }

    }

    @Auth
    @PostMapping("/userNameList")
    @ApiOperation(value = "根据姓名获取用户姓名创赛号列表", response = BriefUserName.class)
    public Result getBriefUserNameListByName(@NotNull @Valid @RequestBody GetBriefUserNameListRequest request) {
        try {
            return  Result.success(userService.searchBriefUserNameListByName(request));
        }catch (CommonException e){
            return  Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @PostMapping("/team")
    @ApiOperation(value = "查看我的组队", response = BriefProjectInformation.class)
    public Result getBriefTeamList(@NotNull @Valid @RequestBody GetTeamListRequest request) {
        try {
            return  Result.success(userService.getBriefTeamList(request,sessionUtils.getUserChuangNum()));
        }catch (CommonException e){
            return  Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @PostMapping("/logout")
    @ApiOperation(value = "登出",response = String.class)
    public Result logout(){
        try {
            String userChuangNum = sessionUtils.getUserChuangNum();
            sessionUtils.invalidate();
            return Result.success(userChuangNum);
        }catch (CommonException e){
            return  Result.result(e.getCommonErrorCode());
        }
    }


    @GetMapping("/check")
    @ApiOperation(value = "检查登录态")
    public Result checkSession(){
        try {
            if(sessionUtils.getSessionData()==null) return Result.fail("登录失效");
            return Result.success("已登录");
        }catch (CommonException e){
            return Result.result(e.getCommonErrorCode());
        }

    }

    @Auth
    @PostMapping(value = "/resumeUpload", produces = "application/json")
    @ApiOperation(value = "上传个人简历")
    @ApiImplicitParam(name="fileData",value="文件名",required = true,paramType = "query",dataType = "String")
    public Result resumeUpload(MultipartFile file,@NotNull @RequestParam("name") String name) {
        try {
            return Result.success(userService.resumeUpload(sessionUtils.getUserChuangNum(),file,name));
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @PostMapping(value = "/resumeDelete", produces = "application/json")
    @ApiOperation(value = "删除用户简历", response = String.class)
    @ApiImplicitParam(name = "url", value = "简历url", required = true, paramType = "query", dataType = "String")
    public Result resumeDelete(@NotNull @RequestParam("url") String url) {
        try {
            return Result.success(userService.resumeDelete(url,sessionUtils.getUserChuangNum()));
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

    @GetMapping(value = "/experience", produces = "application/json")
    @ApiOperation(value = "获取用户项目经历")
    @ApiImplicitParam(name="userChuangNum",value="用户创赛号",required = true,paramType = "query",dataType = "String")
    public Result getUserProjectExperience(@NotNull @RequestParam("userChuangNum")String userChuangNum) {
        try {
            return Result.success(userService.getUserProjectExperience(userChuangNum));
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

    @GetMapping(value = "/downloadResume/{flag}",produces = "application/json")
    @ApiOperation(value = "下载简历附件（pdf或markdown）,整个链接upload接口曾经给过")
    public Result downloadResume(@PathVariable String flag, HttpServletResponse response){
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
