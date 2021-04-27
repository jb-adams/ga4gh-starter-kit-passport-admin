package org.ga4gh.starterkit.passport.controller;

import java.util.List;

import org.ga4gh.starterkit.passport.model.PassportVisa;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/ga4gh/passports/v1/visas")
public class Visas {

    @GetMapping
    public List<PassportVisa> getPassportVisas() {
        return null;
    }

    @GetMapping(path = "/{visaId:.+}")
    public PassportVisa getPassportVisa(
        @PathVariable(name = "visaId") String visaId
    ) {
        return null;
    }

    @PostMapping
    public PassportVisa createPassportVisa(
        @RequestBody PassportVisa passportVisa
    ) {
        return null;
    }

    @PutMapping(path = "/{visaId:.+}")
    public PassportVisa updatePassportVisa(
        @PathVariable(name = "visaId") String visaId,
        @RequestBody PassportVisa passportVisa
    ) {
        return null;
    }

    @DeleteMapping(path = "/{visaId:.+}")
    public PassportVisa deletePassportVisa(
        @PathVariable(name = "visaId") String visaId
    ) {
        return null;
    }
}
