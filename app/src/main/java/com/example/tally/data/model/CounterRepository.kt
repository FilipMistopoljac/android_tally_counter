package com.example.tally.data.model

class CounterRepository(
    delegate: CounterAPI
) : CounterAPI by delegate {

}