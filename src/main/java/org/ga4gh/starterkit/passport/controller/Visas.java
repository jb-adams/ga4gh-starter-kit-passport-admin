package org.ga4gh.starterkit.passport.controller;

import java.util.List;
import javax.annotation.Resource;
import com.fasterxml.jackson.annotation.JsonView;
import org.ga4gh.starterkit.common.requesthandler.BasicCreateRequestHandler;
import org.ga4gh.starterkit.common.requesthandler.BasicDeleteRequestHandler;
import org.ga4gh.starterkit.common.requesthandler.BasicShowRequestHandler;
import org.ga4gh.starterkit.common.requesthandler.BasicUpdateRequestHandler;
import org.ga4gh.starterkit.passport.model.PassportVisa;
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
@RequestMapping("/admin/ga4gh/passport/v1/visas")
public class Visas {

    @Autowired
    private PassportHibernateUtil hibernateUtil;

    @Resource(name = "showVisaRequestHandler")
    private BasicShowRequestHandler<String, PassportVisa> showVisa;

    @Resource(name = "createVisaRequestHandler")
    private BasicCreateRequestHandler<String, PassportVisa> createVisa;

    @Resource(name = "updateVisaRequestHandler")
    private BasicUpdateRequestHandler<String, PassportVisa> updateVisa;

    @Resource(name = "deleteVisaRequestHandler")
    private BasicDeleteRequestHandler<String, PassportVisa> deleteVisa;

    @GetMapping
    @JsonView(SerializeView.Visa.class)
    public List<PassportVisa> getPassportVisas() {
        return hibernateUtil.getPassportVisas();
    }

    @GetMapping(path = "/{visaId:.+}")
    @JsonView(SerializeView.VisaRelational.class)
    public PassportVisa getPassportVisa(
        @PathVariable(name = "visaId") String visaId
    ) {
        return showVisa.prepare(visaId).handleRequest();
    }

    @PostMapping
    @JsonView(SerializeView.VisaRelational.class)
    public PassportVisa createPassportVisa(
        @RequestBody PassportVisa passportVisa
    ) {
        setBidirectionalRelationship(passportVisa);
        return createVisa.prepare(passportVisa).handleRequest();
    }

    @PutMapping(path = "/{visaId:.+}")
    @JsonView(SerializeView.VisaRelational.class)
    public PassportVisa updatePassportVisa(
        @PathVariable(name = "visaId") String visaId,
        @RequestBody PassportVisa passportVisa
    ) {
        setBidirectionalRelationship(passportVisa);
        return updateVisa.prepare(visaId, passportVisa).handleRequest();
    }

    @DeleteMapping(path = "/{visaId:.+}")
    @JsonView(SerializeView.VisaRelational.class)
    public PassportVisa deletePassportVisa(
        @PathVariable(name = "visaId") String visaId
    ) {
        return deleteVisa.prepare(visaId).handleRequest();
    }

    private void setBidirectionalRelationship(PassportVisa passportVisa) {
        if (passportVisa.getPassportVisaAssertions() != null) {
            passportVisa.getPassportVisaAssertions().forEach(assertion -> assertion.setPassportVisa(passportVisa));
        }
    }
}
