package com.example.task1

import com.apollographql.apollo3.ApolloClient

val apolloClient = ApolloClient.Builder()
    .serverUrl("https://countries.trevorblades.com/graphql")
    .build()
