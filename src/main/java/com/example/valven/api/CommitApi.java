package com.example.valven.api;

import com.example.valven.util.Platform;
import com.example.valven.util.response.ValvenResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.example.valven.util.api.ValvenApiEndPoints.COMMIT_PATH;

@CrossOrigin(origins = "*")
@RequestMapping(COMMIT_PATH)
public interface CommitApi {

    @PostMapping
    public String fetchAndSaveCommits(@RequestParam Platform platform,
                                      @RequestParam String ownerOrProjectId,
                                      @RequestParam(required = false) String repo,
                                      @RequestParam String token);

    @GetMapping
    public ValvenResponse<Object> getCommits(@RequestHeader Map<String, Object> header,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size);
}
