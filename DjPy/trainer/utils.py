from datetime import timedelta

def booking_time_discovery_test(start_schedule, end_schedule, bookings, duration):
    """
    Находит свободные интервалы
    Сделано для тестов

    """
    all_time_slots=[]

    current_time=start_schedule

    while current_time + timedelta(minutes=duration) <= end_schedule:
        all_time_slots.append(current_time)
        current_time+=timedelta(minutes=15)

    all_time_slots = [slot 
                      for slot in all_time_slots 
                      if not any(booking[0] <= slot < booking[1] 
                                 for booking in bookings)]
    
    valid_time_slots = []
    for slot in all_time_slots:
        next_bookings = [booking[0] 
                         for booking in bookings 
                         if booking[0] > slot]
        next_booking_start = min(next_bookings, default=end_schedule)
        
        if slot + timedelta(minutes=duration) <= next_booking_start:
            valid_time_slots.append(slot)

    return valid_time_slots

def booking_time_discovery(start_schedule, end_schedule, trainer_bookings, duration):

    duration = duration
    
    all_time_slots=[]

    current_time=start_schedule

    while current_time + timedelta(minutes=duration) <= end_schedule:
        all_time_slots.append(current_time)
        current_time+=timedelta(minutes=15)

    if not trainer_bookings:
        return all_time_slots

    all_time_slots = [slot 
                      for slot in all_time_slots 
                      if not any(booking[0] <= slot < booking[1] 
                                 for booking in trainer_bookings)]
    
    valid_time_slots = []
    for slot in all_time_slots:
        next_bookings = [booking[0] 
                         for booking in trainer_bookings 
                         if booking[0] > slot]
        next_booking_start = min(next_bookings, default=end_schedule)
        
        if slot + timedelta(minutes=duration) <= next_booking_start:
            valid_time_slots.append(slot)

    return valid_time_slots


