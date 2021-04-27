package org.ga4gh.starterkit.passport.controller;

import java.util.List;
import javax.annotation.Resource;
import com.fasterxml.jackson.annotation.JsonView;
import org.ga4gh.starterkit.common.requesthandler.BasicCreateRequestHandler;
import org.ga4gh.starterkit.common.requesthandler.BasicDeleteRequestHandler;
import org.ga4gh.starterkit.common.requesthandler.BasicShowRequestHandler;
import org.ga4gh.starterkit.common.requesthandler.BasicUpdateRequestHandler;
import org.ga4gh.starterkit.passport.model.PassportUser;
import org.ga4gh.starterkit.passport.utils.SerializeView;
import org.ga4gh.starterkit.passport.utils.hibernate.PassportHibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/ga4gh/passport/v1/users")
public class Users {

    @Autowired
    private PassportHibernateUtil hibernateUtil;

    @Resource(name = "showUserRequestHandler")
    private BasicShowRequestHandler<String, PassportUser> showUser;

    @Resource(name = "createUserRequestHandler")
    private BasicCreateRequestHandler<String, PassportUser> createUser;

    @Resource(name = "updateUserRequestHandler")
    private BasicUpdateRequestHandler<String, PassportUser> updateUser;

    @Resource(name = "deleteUserRequestHandler")
    private BasicDeleteRequestHandler<String, PassportUser> deleteUser;

    @GetMapping
    @JsonView(SerializeView.User.class)
    public List<PassportUser> getPassportUsers() {
        return hibernateUtil.getPassportUsers();
    }

    @GetMapping(path = "/{userId:.+}")
    @JsonView(SerializeView.UserRelational.class)
    public PassportUser getPassportUser(
        @PathVariable(name = "userId") String userId
    ) {
        return showUser.prepare(userId).handleRequest();
    }

    @PostMapping
    @JsonView(SerializeView.UserRelational.class)
    public PassportUser createPassportUser(
        @RequestBody PassportUser passportUser
    ) {
        setBidirectionalRelationship(passportUser);
        return createUser.prepare(passportUser).handleRequest();
    }

    @PutMapping(path = "/{userId:.+}")
    @JsonView(SerializeView.UserRelational.class)
    public PassportUser updatePassportUser(
        @PathVariable(name = "userId") String userId,
        @RequestBody PassportUser passportUser
    ) {
        setBidirectionalRelationship(passportUser);
        return updateUser.prepare(userId, passportUser).handleRequest();
    }

    @DeleteMapping(path = "/{userId:.+}")
    @JsonView(SerializeView.UserRelational.class)
    public PassportUser deletePassportUser(
        @PathVariable(name = "userId") String userId
    ) {
        return deleteUser.prepare(userId).handleRequest();
    }

    private void setBidirectionalRelationship(PassportUser passportUser) {
        if (passportUser.getPassportVisaAssertions() != null) {
            passportUser.getPassportVisaAssertions().forEach(assertion -> assertion.setPassportUser(passportUser));
        }
    }
}
