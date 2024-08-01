package com.example.valven.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.valven.util.api.ValvenApiEndPoints.DEVELOPER_PATH;

@CrossOrigin(origins = "*")
@RequestMapping(DEVELOPER_PATH)
public interface DeveloperApi {


}
