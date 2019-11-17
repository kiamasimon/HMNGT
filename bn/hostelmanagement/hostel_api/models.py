from django.contrib.auth import get_user_model
from django.db import models


class Student(get_user_model()):
    phone_number = models.IntegerField(null=True)
    religion = models.CharField(max_length=250, null=True)
    hobbies = models.CharField(max_length=250, null=True)
    sports = models.CharField(max_length=250, null=True)
    school = models.CharField(max_length=250, null=True)
    settled = models.BooleanField(null=True, default=False)
    course = models.CharField(max_length=250, null=True)
    drugs = models.CharField(max_length=250, null=True)
    visitor_count = models.CharField(max_length=250, null=True)
    gender = models.CharField(max_length=1, null=True)


class Manager(get_user_model()):
    phone_number = models.IntegerField()


class Room(models.Model):
    room_number = models.CharField(max_length=250)
    type = models.CharField(max_length=250)
    status_occupied = models.CharField(max_length=250, null=True)
    short_description = models.CharField(max_length=250)
    floor = models.CharField(max_length=250, null=True)


class StudentRoom(models.Model):
    student = models.ForeignKey(Student, on_delete=models.CASCADE)
    room = models.ForeignKey(Room, on_delete=models.CASCADE)


class StudentBooking(models.Model):
    student = models.ForeignKey(Student, on_delete=models.CASCADE)
    room = models.ForeignKey(Room, on_delete=models.CASCADE)
    approvement_status = models.BooleanField(default=False)


class StudentSelections(models.Model):
    student = models.ForeignKey(Student, related_name='student', on_delete=models.CASCADE)
    possible_roommate = models.ForeignKey(Student, related_name='roommate', on_delete=models.CASCADE)
    pairing_status = models.CharField(max_length=250)


class MpesaPayment(models.Model):
    room = models.ForeignKey(Room, on_delete=models.CASCADE)
    student = models.ForeignKey(Student, on_delete=models.CASCADE)
    status_payed = models.BooleanField(default=False)

