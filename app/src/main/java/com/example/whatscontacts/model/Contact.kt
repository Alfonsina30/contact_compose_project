package com.example.whatscontacts.model

data class Contact(val id: Int, val name: String, val phone: String)

val fakeContacts = listOf(
    Contact(1, "Roberto M", "+13472886504"),
    Contact(2, "Rossiel Nuevo", "+16047602089"),
    Contact(3, "Manuel Rosas", "+18093090271"),
    Contact(4, "El Goldo", "+13215576133"),
    Contact(5, "Eduar", "+13477549383"),
    Contact(6, "Silvio", "+14077316247"),
    Contact(7, "Alquiler Corotos", "+18093180964"),
    Contact(8, "Facebook Pedido", "+15550247390"),
)
