package com.example.task1.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinDetailsDTO(
    @Expose
    var description: String? = "",

    @SerializedName("development_status")
    var developmentStatus: String? = "",

    @SerializedName("first_data_at")
    var firstDataAt: String? = "",

    @SerializedName("hardware_wallet")
    var hardwareWallet: Boolean? = false,

    @SerializedName("hash_algorithm")
    var hashAlgorithm: String? = "",

    @Expose
    var id: String? = "",

    @SerializedName("is_active")
    var isActive: Boolean? = false,

    @SerializedName("is_new")
    var isNew: Boolean? = false,

    @SerializedName("last_data_at")
    var lastDataAt: String? = "",

    @Expose
    var links: LinksModel? = null,

    @SerializedName("links_extended")
    var linksExtended: List<LinksExtended>? = emptyList(),

    @Expose
    var message: String? = "",

    @Expose
    var name: String? = "",

    @SerializedName("open_source")
    var openSource: Boolean? = false,

    @SerializedName("org_structure")
    var orgStructure: String? = "",

    @SerializedName("proof_type")
    var proofType: String? = "",

    @Expose
    var rank: Long? = 0,

    @SerializedName("started_at")
    var startedAt: String? = "",

    @Expose
    var symbol: String? = "",

    @Expose
    var tags: List<TagModel>? = emptyList(),

    @Expose
    var team: List<TeamModel>? = emptyList(),

    @Expose
    var type: String? = "",

    @Expose
    var whitepaper: WhitepaperModel? = null,

    val contract: String? = "",
    val platform: String? = ""
)