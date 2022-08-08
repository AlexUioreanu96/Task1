package com.example.task1

import com.apollographql.apollo3.ApolloClient

class ApolloClient {
    public val apolloClient = ApolloClient.Builder()
        .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
        .build()
}