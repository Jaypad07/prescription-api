package com.example.prescriptionapi.controller;

import com.example.prescriptionapi.exception.InformationExistException;
import com.example.prescriptionapi.model.Patient;
import com.example.prescriptionapi.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class PatientController {
   private PatientRepository patientRepository;

    @Autowired
    public void setPatientRepository(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // http://localhost:9097/api/patient
    @GetMapping(path = "/patient")
    public List<Patient> getAllPatients(){
        return patientRepository.findAll();
    }

    // http://localhost:9097/api/patient
    @PostMapping(path = "/patient")
    public Patient createPatient(@RequestBody Patient patientObject) {
        Optional<Patient> patient = patientRepository.findPatientBySocialSecurity(patientObject.getSocialSecurity());
        if (patient.isPresent()) {
            throw new InformationExistException("Patient with id " + patientObject.getId() + " already exists");
        }else return patientRepository.save(patientObject);
    }

    // http://localhost:9097/api/patient
    @PostMapping(path = "/patient/{name}")
    public List<Patient> findPatientsByName(@PathVariable String name) {
        return patientRepository.findPatientByName(name);
    }

}
