package com.phoenix.huashi.controller.team;


import com.phoenix.huashi.common.Result;
import com.phoenix.huashi.service.team.TeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("队伍")
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping("/add")
    @ApiOperation("添加队伍")
    public Result AddCar(){
        teamService.addTeam();
        return Result.success("yeah");
    }
}
