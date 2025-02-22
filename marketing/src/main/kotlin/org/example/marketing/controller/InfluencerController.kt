package org.example.marketing.controller

import org.example.marketing.dto.user.request.GetInfluencerInfoRequest
import org.example.marketing.dto.user.request.MakeNewInfluencerRequest
import org.example.marketing.dto.user.response.GetInfluencerInfoResponse
import org.example.marketing.dto.user.response.MakeNewInfluencerResponse
import org.example.marketing.enum.FrontErrorCode
import org.example.marketing.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class InfluencerController(
    private val userService: UserService
) {
    @PostMapping("/test/influencer")
    fun makeInfluencer(
        @RequestBody request: MakeNewInfluencerRequest
    ): ResponseEntity<MakeNewInfluencerResponse> {
        val newId = userService.saveInfluencer(request)

        return if (newId != null) {
            ResponseEntity.status(HttpStatus.CREATED).body(
                MakeNewInfluencerResponse.of(
                    FrontErrorCode.OK.code,
                    FrontErrorCode.OK.message,
                    newId
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        }

    }

    // for test
    @GetMapping("/test/influencer-all")
    fun findAll(): ResponseEntity<List<GetInfluencerInfoResponse>> {
        return ResponseEntity.ok().body(
            userService.findAllInfluencers().map {
                GetInfluencerInfoResponse.of(it, FrontErrorCode.OK.code, FrontErrorCode.OK.message)
            }
        )
    }

    @GetMapping("/test/influencer")
    fun findById(
        @RequestBody request: GetInfluencerInfoRequest
    ): ResponseEntity<GetInfluencerInfoResponse> {
        val foundedInfluencer = userService.findInfluencerById(request.influencerId)
        return if (foundedInfluencer != null) {
            ResponseEntity.ok().body(
                GetInfluencerInfoResponse.of(
                    foundedInfluencer,
                    FrontErrorCode.OK.code,
                    FrontErrorCode.OK.message
                )
            )
        } else {
            ResponseEntity.notFound().build()
        }
    }
}