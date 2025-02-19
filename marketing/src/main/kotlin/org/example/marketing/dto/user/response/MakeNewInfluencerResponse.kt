package org.example.marketing.dto.user.response

import org.example.marketing.dto.error.FrontErrorResponse

data class MakeNewInfluencerResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String?,
    val CreatedId: Long,
): FrontErrorResponse(frontErrorCode, errorMessage)