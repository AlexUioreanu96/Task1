package com.example.task1.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName

data class CoinDetailsDTO(
    @Expose
    val description: String = "",
    @SerialName("development_status")
    val developmentStatus: String = "",
    @SerializedName("first_data_at")
    val firstDataAt: String = "",
    @SerializedName("hardware_wallet")
    val hardwareWallet: Boolean = false,
    @SerializedName("hash_algorithm")
    val hashAlgorithm: String = "",
    @Expose
    val id: String = "",
    @SerializedName("is_active")
    val isActive: Boolean = false,
    @SerializedName("is_new")
    val isNew: Boolean = false,
    @SerializedName("last_data_at")
    val lastDataAt: String = "",
    @Expose
    val links: LinksModel = LinksModel(),
    @SerializedName("links_extended")
    val linksExtended: List<LinksExtended> = emptyList(),
    @Expose
    val message: String = "",
    @Expose
    val name: String = "",
    @SerializedName("open_source")
    val openSource: Boolean = false,
    @SerializedName("org_structure")
    val orgStructure: String = "",
    @SerializedName("proof_type")
    val proofType: String = "",
    @Expose
    val rank: Long = 0,
    @SerializedName("started_at")
    val startedAt: String = "",
    @Expose
    val symbol: String = "",
    @Expose
    val tags: List<TagModel> = emptyList(),
    @Expose
    val team: List<TeamModel> = emptyList(),
    @Expose
    val type: String = "",
    @Expose
    val whitepaper: WhitepaperModel = WhitepaperModel(),
    val contract: String = "",
    val platform: String = "",

    val parent: ParentModel = ParentModel(),

    val contracts: List<ContractModel> = emptyList()

)