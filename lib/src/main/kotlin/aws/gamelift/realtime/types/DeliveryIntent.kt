package aws.gamelift.realtime.types

enum class DeliveryIntent {
	// guaranteed in-order delivery.  e.g. TCP
    Reliable,
    // non-guaranteed, possibly out-of-order delivery.  e.g. UDP
    Fast
}