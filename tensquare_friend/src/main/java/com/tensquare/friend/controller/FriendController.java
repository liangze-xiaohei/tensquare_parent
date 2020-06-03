package com.tensquare.friend.controller;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
@CrossOrigin
public class FriendController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private FriendService friendService;

    /**
     * 添加好友
     * @param friendid 对方用户ID
     * @param type 1：喜欢 0：不喜欢
     * @return
     */
    @RequestMapping(value="/like/{friendid}/{type}",method= RequestMethod.PUT)
    public Result addFriend(@PathVariable String friendid , @PathVariable String type){
        //验证是否登录，并且拿到当前用户的id
        Claims claims = (Claims) request.getAttribute("user_claims");
        if(claims == null){
            return new Result(false,StatusCode.LOGINERROR,"无权访问");
        }
        String userid = claims.getId();
        //判断是添加好友还是添加非好友
        //如果是喜欢
        if(type.equals("1")){
            if(friendService.addFriend(userid,friendid)==0){
                return new Result(false, StatusCode.REPERROR,"已经添加此好友");
            }
        }else{
            //不喜欢
            friendService.addNoFriend(claims.getId(),friendid);//向不喜欢列表中添加记录
        }
        return new Result(true, StatusCode.OK, "操作成功");
    }

    /**
     * 删除好友
     * @param friendid
     * @return
     */
    @RequestMapping(value="/{friendid}",method=RequestMethod.DELETE)
    public Result remove(@PathVariable String friendid) {
        //验证是否登录，并且拿到当前用户的id
        Claims claims=(Claims)request.getAttribute("user_claims");
        if(claims==null){
            return new Result(false, StatusCode.ACCESSERROR,"无权访问");
        }
        friendService.deleteFriend(claims.getId(), friendid);
        return new Result(true, StatusCode.OK, "删除成功");
    }
}
