import unittest , datetime
from trainer.utils import booking_time_discovery_test
from django.test import TestCase , Client
from django.contrib.auth.models import User, Group
from django.urls import reverse
from .models import Category

class TestSchedule(unittest.TestCase):
    def test_no_booking(self):
        schedule_start=datetime.datetime(year=2024,month=12,day=25,hour=9, minute=0)
        schedule_end=datetime.datetime(year=2024,month=12,day=25,hour=14, minute=0)
        trainer_bookings=[]
        search_window= 60
        results=booking_time_discovery_test(schedule_start,schedule_end,trainer_bookings,search_window)
        excepted=[datetime.datetime(year=2024,month=12,day=25,hour=9, minute=0),
                  datetime.datetime(year=2024,month=12,day=25,hour=9, minute=15),
                  datetime.datetime(year=2024,month=12,day=25,hour=9, minute=30),
                  datetime.datetime(year=2024,month=12,day=25,hour=9, minute=45),
                  datetime.datetime(year=2024,month=12,day=25,hour=10, minute=0),
                  datetime.datetime(year=2024,month=12,day=25,hour=10, minute=15),
                  datetime.datetime(year=2024,month=12,day=25,hour=10, minute=30),
                  datetime.datetime(year=2024,month=12,day=25,hour=10, minute=45),
                  datetime.datetime(year=2024,month=12,day=25,hour=11, minute=0),
                  datetime.datetime(year=2024,month=12,day=25,hour=11, minute=15),
                  datetime.datetime(year=2024,month=12,day=25,hour=11, minute=30),
                  datetime.datetime(year=2024,month=12,day=25,hour=11, minute=45),
                  datetime.datetime(year=2024,month=12,day=25,hour=12, minute=0),
                  datetime.datetime(year=2024,month=12,day=25,hour=12, minute=15),
                  datetime.datetime(year=2024,month=12,day=25,hour=12, minute=30),
                  datetime.datetime(year=2024,month=12,day=25,hour=12, minute=45),
                  datetime.datetime(year=2024,month=12,day=25,hour=13, minute=0),
                  ]
        self.assertListEqual(excepted,results)
    
    def test_full_booking(self):
        schedule_start = datetime.datetime(year=2024,month=12,day=25,hour=9, minute=0)
        schedule_end = datetime.datetime(year=2024,month=12,day=25,hour=10, minute=0)
        trainer_bookings = [(schedule_start, schedule_end)]
        search_window = 30
        result = booking_time_discovery_test(schedule_start, schedule_end, trainer_bookings, search_window)
        expected = []
        self.assertEqual(result, expected)
    
    def test_single_booking(self):
        schedule_start = datetime.datetime(year=2024,month=12,day=25,hour=9, minute=0)
        schedule_end = datetime.datetime(year=2024,month=12,day=25,hour=12, minute=0)
        search_window = 30

        trainer_bookings = [(datetime.datetime(year=2024,month=12,day=25,hour=9, minute=0), 
                            datetime.datetime(year=2024,month=12,day=25,hour=10, minute=0))]
        
        result = booking_time_discovery_test(schedule_start, schedule_end, trainer_bookings, search_window)

        expected = [
                datetime.datetime(year=2024,month=12,day=25,hour=10, minute=0),
                datetime.datetime(year=2024,month=12,day=25,hour=10, minute=15),
                datetime.datetime(year=2024,month=12,day=25,hour=10, minute=30),
                datetime.datetime(year=2024,month=12,day=25,hour=10, minute=45),
                datetime.datetime(year=2024,month=12,day=25,hour=11, minute=0),
                datetime.datetime(year=2024,month=12,day=25,hour=11, minute=15),
                datetime.datetime(year=2024,month=12,day=25,hour=11, minute=30),
            ]
        self.assertEqual(result, expected)
    
    def test_many_booking_1(self):
        schedule_start = datetime.datetime(year=2024,month=12,day=25,hour=9, minute=0)
        schedule_end = datetime.datetime(year=2024,month=12,day=25,hour=12, minute=0)
        search_window = 30

        trainer_bookings = [(datetime.datetime(year=2024,month=12,day=25,hour=9, minute=0), datetime.datetime(year=2024,month=12,day=25,hour=10, minute=0)),
                            (datetime.datetime(year=2024,month=12,day=25,hour=10, minute=15), datetime.datetime(year=2024,month=12,day=25,hour=10, minute=45)),]
        
        result = booking_time_discovery_test(schedule_start, schedule_end, trainer_bookings, search_window)

        expected = [
                datetime.datetime(year=2024,month=12,day=25,hour=10, minute=45),
                datetime.datetime(year=2024,month=12,day=25,hour=11, minute=0),
                datetime.datetime(year=2024,month=12,day=25,hour=11, minute=15),
                datetime.datetime(year=2024,month=12,day=25,hour=11, minute=30),
            ]
        
        self.assertEqual(result, expected)
    
    def test_many_booking_2(self):
        schedule_start = datetime.datetime(year=2024,month=12,day=25,hour=9, minute=0)
        schedule_end = datetime.datetime(year=2024,month=12,day=25,hour=13, minute=0)
        search_window = 30
        
        trainer_bookings = [(datetime.datetime(year=2024,month=12,day=25,hour=9, minute=0), datetime.datetime(year=2024,month=12,day=25,hour=9, minute=30)),
                            (datetime.datetime(year=2024,month=12,day=25,hour=10, minute=15), datetime.datetime(year=2024,month=12,day=25,hour=10, minute=45)),
                            (datetime.datetime(year=2024,month=12,day=25,hour=11, minute=0), datetime.datetime(year=2024,month=12,day=25,hour=11, minute=30)),
                            (datetime.datetime(year=2024,month=12,day=25,hour=12, minute=30), datetime.datetime(year=2024,month=12,day=25,hour=13, minute=0)),]
        
        result = booking_time_discovery_test(schedule_start, schedule_end, trainer_bookings, search_window)

        expected = [
                datetime.datetime(year=2024,month=12,day=25,hour=9, minute=30),
                datetime.datetime(year=2024,month=12,day=25,hour=9, minute=45),
                datetime.datetime(year=2024,month=12,day=25,hour=11, minute=30),
                datetime.datetime(year=2024,month=12,day=25,hour=11, minute=45),
                datetime.datetime(year=2024,month=12,day=25,hour=12, minute=0),
            ]
        
        self.assertEqual(result, expected)
    
    def test_smoll_booking(self):
        schedule_start = datetime.datetime(year=2024,month=12,day=25,hour=9, minute=0)
        schedule_end = datetime.datetime(year=2024,month=12,day=25,hour=12, minute=0)
        search_window = 10
        
        trainer_bookings = [(datetime.datetime(year=2024,month=12,day=25,hour=9, minute=0), datetime.datetime(year=2024,month=12,day=25,hour=9, minute=30)),
                            (datetime.datetime(year=2024,month=12,day=25,hour=10, minute=15), datetime.datetime(year=2024,month=12,day=25,hour=10, minute=45)),
                            (datetime.datetime(year=2024,month=12,day=25,hour=11, minute=30), datetime.datetime(year=2024,month=12,day=25,hour=12, minute=0)),
                            ]
        
        result = booking_time_discovery_test(schedule_start, schedule_end, trainer_bookings, search_window)

        expected = [
                datetime.datetime(year=2024,month=12,day=25,hour=9, minute=30),
                datetime.datetime(year=2024,month=12,day=25,hour=9, minute=45),
                datetime.datetime(year=2024,month=12,day=25,hour=10, minute=0),
                datetime.datetime(year=2024,month=12,day=25,hour=10, minute=45),
                datetime.datetime(year=2024,month=12,day=25,hour=11, minute=0),
                datetime.datetime(year=2024,month=12,day=25,hour=11, minute=15),
            ]
        
        self.assertEqual(result, expected)
    
    def test_unusual_booking(self):
        schedule_start = datetime.datetime(year=2024,month=12,day=25,hour=9, minute=0)
        schedule_end = datetime.datetime(year=2024,month=12,day=25,hour=12, minute=0)
        search_window = 30
        
        trainer_bookings = [(datetime.datetime(year=2024,month=12,day=25,hour=9, minute=12), datetime.datetime(year=2024,month=12,day=25,hour=9, minute=46)),
                            (datetime.datetime(year=2024,month=12,day=25,hour=10, minute=11), datetime.datetime(year=2024,month=12,day=25,hour=10, minute=33)),
                            (datetime.datetime(year=2024,month=12,day=25,hour=11, minute=32), datetime.datetime(year=2024,month=12,day=25,hour=12, minute=0)),
                        ]
        
        result = booking_time_discovery_test(schedule_start, schedule_end, trainer_bookings, search_window)

        expected = [
                datetime.datetime(year=2024,month=12,day=25,hour=10, minute=45),
                datetime.datetime(year=2024,month=12,day=25,hour=11, minute=0),
 
            ]
        
        self.assertEqual(result, expected)

class TrainerTest(TestCase):
    def show_all_trainers(self):
        client= Client()
        response = client.get('/trainers/')
        self.assertEqual(response.status_code,200)

    def test_trainer_page_get_authenticated(self):
        client = Client()

        user = User(username="test_trainer")
        user.set_password("password123")
        user.save()

        trainer_group = Group(name="Trainer")
        trainer_group.save()
        user.groups.add(trainer_group)

        client.login(username="test_trainer", password="password123")

        trainer_id = user.id 

        response = client.get(f"/trainers/{trainer_id}/")

        self.assertEqual(response.status_code, 200)

        self.assertIn("categories", response.context)
        self.assertIn("services", response.context)
        self.assertIn("trainer", response.context)

    def test_trainer_page_redirect_if_not_authenticated(self):
        client = Client()
        trainer_id = 1 

        response = client.get(f"/trainers/{trainer_id}/")

        self.assertEqual(response.status_code, 302)
        self.assertTrue(response.url.startswith("/accounts/login/"))

    def test_create_service_success(self):
        client = Client()

        user = User.objects.create_user(username="test_trainer", password="password123")

        trainer_group = Group.objects.create(name="Trainer")
        user.groups.add(trainer_group)

        client.login(username="test_trainer", password="password123")

        trainer_id = user.id
        category = Category.objects.create(name="Fitness")

        form_data = {
            "category": category.id,
            "price": 50,
            "level": 2,
            "duration": 60,
        }

        response = client.post(reverse("create_service", kwargs={"trainer_id": trainer_id}), data=form_data)

        self.assertEqual(response.status_code, 302)
        self.assertEqual(response.url, reverse("trainer_page", kwargs={"trainer_id": trainer_id}))

    def test_create_service_redirect_if_not_trainer(self):
        client = Client()

        user = User.objects.create_user(username="test_user", password="password123")

        client.login(username="test_user", password="password123")

        trainer_id = user.id
        form_data = {
            "category": 1,
            "price": 50,
            "level": 2,
            "duration": 60,
        }
        response = client.post(reverse("create_service", kwargs={"trainer_id": trainer_id}), data=form_data)

        self.assertEqual(response.status_code, 302)
        self.assertEqual(response.url, reverse("trainer_page", kwargs={"trainer_id": trainer_id}))
    