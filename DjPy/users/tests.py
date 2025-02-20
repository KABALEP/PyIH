from django.test import TestCase, Client
from django.contrib.auth.models import User, Group
from django.urls import reverse
from booking.models import Booking

class UserPageTest(TestCase):

    def test_user_page_redirect_if_not_logged_in(self):
        client = Client()

        response = client.get(reverse('user_page'))
        
        self.assertEqual(response.status_code, 302)
        
    def test_login_page_get(self):
        client = Client()
        response = client.get('/login/')

        self.assertEqual(response.status_code, 200)

    def test_login_page_post_success(self):
        client = Client()

        user = User.objects.create_user(username='testuser', password='password123')

        response = client.post(reverse('login'), data={'user_name': 'testuser', 'password': 'password123'})

        self.assertEqual(response.status_code, 302)
        self.assertRedirects(response, reverse('login'))

    def test_login_page_post_failure(self):
        client = Client()

        User.objects.create_user(username='testuser', password='password123')

        response = client.post(reverse('login'), data={'user_name': 'testuser', 'password': 'wrongpassword'})

        self.assertEqual(response.status_code, 200)

    def test_logout_page(self):
        client = Client()

        user = User.objects.create_user(username='testuser', password='password123')
        client.login(username='testuser', password='password123')

        response = client.get(reverse('logout'))

        self.assertEqual(response.status_code, 200)

    def test_register_page_get(self):
        client = Client()
        response = client.get(reverse('register'))

        self.assertEqual(response.status_code, 200)

    def test_register_page_post_existing_user(self):
        client = Client()

        User.objects.create_user(username='existinguser', password='password123')

        response = client.post(reverse('register'), data={
            'user_name': 'existinguser',
            'password': 'password123',
            'first_name': 'John',
            'last_name': 'Doe',
            'email': 'john.doe@example.com',
        })

        self.assertEqual(response.status_code, 200)