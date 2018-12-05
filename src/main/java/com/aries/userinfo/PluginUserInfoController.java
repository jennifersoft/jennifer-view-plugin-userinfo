package com.aries.userinfo;

import com.aries.extension.starter.PluginController;
import com.aries.extension.util.ConfigUtil;
import com.aries.extension.util.PropertyUtil;
import com.aries.view.dao.file.GroupV1DaoImpl;
import com.aries.view.dao.file.UserDaoImpl;
import com.aries.view.db.DBConf;
import com.aries.view.domain.Group;
import com.aries.view.domain.User;
import com.aries.view.service.GroupService;
import com.aries.view.service.GroupServiceImpl;
import com.aries.view.service.UserService;
import com.aries.view.service.UserServiceImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class PluginUserInfoController extends PluginController {
    private final GroupService groupService;
    private final UserService userService;

    public PluginUserInfoController()
    {
        // TODO: 개발시에는 defaultValue를 참조하고, 제니퍼 화면에서는 기존의 DB 경로를 그대로 사용함
        DBConf.db_path = ConfigUtil.getValue("db_path", "/Users/alvin/Documents/Test/jennifer5_backup/db_view");

        this.groupService = new GroupServiceImpl(new GroupV1DaoImpl());
        this.userService = new UserServiceImpl(new UserDaoImpl());
    }

    @RequestMapping(value = { "/userinfo" }, method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, String>> getMainPage(@RequestParam String domainId) {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        List<Group> groups = this.groupService.list();
        List<User> users = this.userService.list();

        Map<String, Boolean> existGroupIds = new HashMap<String, Boolean>();
        for(Group group : groups) {
            try {
                JSONObject json = new JSONObject(group.getTarget());
                if(json.has(domainId)) {
                    existGroupIds.put(group.getId(), true);
                }
            } catch (JSONException e) {
                System.out.println(e);
            }
        }

        for (User user : users) {
            if(existGroupIds.containsKey(user.getGroupId())) {
                Map<String, String> userMap = new HashMap<String, String>();
                userMap.put("id", user.getId());
                userMap.put("name", user.getName());
                userMap.put("phone", user.getCellphone());
                userMap.put("email", user.getEmail());
                result.add(userMap);
            }
        }

        return result;
    }
}