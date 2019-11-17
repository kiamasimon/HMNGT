from rest_framework import serializers

from hostel_api.models import *


class StudentSerializer(serializers.ModelSerializer):
    class Meta:
        model = Student
        fields = ['id', 'first_name', 'last_name', 'email', 'phone_number', 'hobbies', 'sports', 'religion', 'course', 'drugs', 'visitor_count']


class RoomSerializer(serializers.ModelSerializer):
    class Meta:
        model = Room
        fields = ['id', 'room_number', 'type', 'status_occupied', 'short_description', 'floor',]