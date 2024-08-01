package com.example.valven.api;

import com.example.valven.util.response.ValvenResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.example.valven.util.api.ValvenApiEndPoints.DEVELOPER_PATH;

@CrossOrigin(origins = "*")
@RequestMapping(DEVELOPER_PATH)
public interface DeveloperApi {

    @GetMapping
    public ValvenResponse<Object> getDeveloperByUsername(@RequestParam String username);

}
