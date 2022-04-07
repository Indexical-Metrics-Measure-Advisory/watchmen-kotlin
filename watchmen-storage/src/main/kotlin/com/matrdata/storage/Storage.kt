package com.matrdata.storage

interface Storage {
    fun connect(): Nothing

    fun close(): Nothing
}