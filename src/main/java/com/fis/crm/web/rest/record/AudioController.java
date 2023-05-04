package com.fis.crm.web.rest.record;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class AudioController {

    private final AudioService audioService;

    public AudioController(AudioService audioService) {
        this.audioService = audioService;
    }

    @GetMapping("/record/{fileType}/{fileName}")
    public Mono<ResponseEntity<byte[]>> streamVideo(@RequestHeader(value = "Range", required = false) String httpRangeList,
                                                    @PathVariable("fileType") String fileType,
                                                    @PathVariable("fileName") String fileName) {
        return Mono.just(audioService.prepareContent(fileName, fileType, httpRangeList));
    }
}
