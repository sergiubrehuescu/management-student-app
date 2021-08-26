package com.example.StudentManagementFullStack.controller;


import com.example.StudentManagementFullStack.dto.RegisterStudentToLiveStreamRequest;
import com.example.StudentManagementFullStack.dto.RegisterStudentToLiveStreamResponse;
import com.example.StudentManagementFullStack.model.LiveStream;
import com.example.StudentManagementFullStack.service.LiveStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="livestream")
public class LiveStreamController {

    private final LiveStreamService liveStreamService;

    @Autowired
    public LiveStreamController(LiveStreamService liveStreamService) { this.liveStreamService = liveStreamService; }

    @PostMapping
    private LiveStream createLiveStream(@RequestBody LiveStream liveStream){
        return liveStreamService.createLiveStream(liveStream);
    }

    @DeleteMapping("/{id}")
    private LiveStream deleteLiveStream(@PathVariable Long id){
        return liveStreamService.deleteLiveStream(id);
    }

    @PutMapping
    private LiveStream updateLiveStream(@RequestBody LiveStream liveStream){
        return liveStreamService.updateLiveStream(liveStream);
    }

    @GetMapping("/{id}")
    private LiveStream getLiveStream(@PathVariable Long id){
        return liveStreamService.getLiveStream(id);
    }

    @PostMapping("registerToLiveStream")
    private RegisterStudentToLiveStreamResponse registerToLiveStream(@RequestBody RegisterStudentToLiveStreamRequest registerStudentToLiveStreamRequest){
        return liveStreamService.registerToLiveStream(registerStudentToLiveStreamRequest);
    }

    @PostMapping("unregisterToLiveStream")
    private RegisterStudentToLiveStreamResponse unregisterToLiveStream(@RequestBody RegisterStudentToLiveStreamRequest registerStudentToLiveStreamRequestDto){
        return liveStreamService.unregisterToLiveStream(registerStudentToLiveStreamRequestDto);
    }

}
