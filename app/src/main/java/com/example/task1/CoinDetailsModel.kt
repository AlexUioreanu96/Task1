package com.example.task1

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinDetailsModel(
    @Expose
    var description: String? = null,

    @SerializedName("development_status")
    var developmentStatus: String? = null,

    @SerializedName("first_data_at")
    var firstDataAt: String? = null,

    @SerializedName("hardware_wallet")
    var hardwareWallet: Boolean? = null,

    @SerializedName("hash_algorithm")
    var hashAlgorithm: String? = null,

    @Expose
    var id: String? = null,

    @SerializedName("is_active")
    var isActive: Boolean? = null,

    @SerializedName("is_new")
    var isNew: Boolean? = null,

    @SerializedName("last_data_at")
    var lastDataAt: String? = null,

    @Expose
    var links: Links? = null,

    @SerializedName("links_extended")
    var linksExtended: List<LinksExtended>? = null,

    @Expose
    var message: String? = null,

    @Expose
    var name: String? = null,

    @SerializedName("open_source")
    var openSource: Boolean? = null,

    @SerializedName("org_structure")
    var orgStructure: String? = null,

    @SerializedName("proof_type")
    var proofType: String? = null,

    @Expose
    var rank: Long? = null,

    @SerializedName("started_at")
    var startedAt: String? = null,

    @Expose
    var symbol: String? = null,

    @Expose
    var tags: List<Tag>? = null,

    @Expose
    var team: List<Team>? = null,

    @Expose
    var type: String? = null,

    @Expose
    var whitepaper: Whitepaper? = null
)