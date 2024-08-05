package com.example.gradecalculator.mapper;

import com.example.gradecalculator.repository.UserSubjectRepository;
import com.example.gradecalculator.service.SubjectSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectSelectionServiceImpl extends SubjectSelectionService {

    @Autowired
    private UserSubjectRepository userSubjectRepository;
}
