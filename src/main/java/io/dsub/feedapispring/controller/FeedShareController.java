package io.dsub.feedapispring.controller;

import io.dsub.feedapispring.api.v1.model.FeedShareDto;
import io.dsub.feedapispring.api.v1.response.FeedShareResponse;
import io.dsub.feedapispring.api.v1.service.FeedShareDtoService;
import io.dsub.feedapispring.domain.FeedShare;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/")
public class FeedShareController {

    private FeedShareDtoService service;

    public FeedShareController(FeedShareDtoService service) {
        this.service = service;
    }

    @PutMapping("/feeds/{feedId}/shares/{userId}")
    @ResponseBody
    public ResponseEntity<FeedShareResponse> addFeedShare(
            @PathVariable("feedId") Long feedId,
            @PathVariable("userId") Long userId) {
        FeedShareDto feedShareDto = new FeedShareDto();
        feedShareDto.setFeedId(feedId);
        feedShareDto.setUserId(userId);
        FeedShareResponse response = service.feedShare(feedShareDto);

        HttpHeaders headers = new HttpHeaders();

        if (response.getData() == null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        } else {
            headers.add("Location",
                    "/feeds/" + response.getData().getFeedId()
                            + "/shares/" + response.getData().getUserId());
            return new ResponseEntity<>(response, headers, HttpStatus.OK);
        }
    }

    @GetMapping("/feeds/{feedId}/shares")
    @ResponseBody
    public ResponseEntity<FeedShareResponse> getFeedShare(
            @PathVariable("feedId") Long feedId) {
        FeedShareResponse response = service.getFeedShares(feedId);
        if (response.getError() != null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping("/feeds/{feedId}/shares/{userId}")
    @ResponseBody
    public ResponseEntity<FeedShareResponse> getFeedShareDetails(
            @PathVariable("feedId") Long feedId,
            @PathVariable("userId") Long userId) {
        FeedShareResponse response = service.getFeedShareDetails(feedId, userId);
        if (response.getError() != null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
