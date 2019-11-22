import base64
import json
from datetime import datetime
from pprint import pprint

import requests
from django.contrib.auth import authenticate
from django.contrib.auth.hashers import make_password
from django.http import JsonResponse
from django.shortcuts import render

# Create your views here.
from django.views.decorators.csrf import csrf_exempt
from requests.auth import HTTPBasicAuth
from rest_framework import status
from rest_framework.authtoken.models import Token
from rest_framework.decorators import api_view, permission_classes
from rest_framework.permissions import AllowAny
from rest_framework.response import Response
from rest_framework_simplejwt.tokens import RefreshToken
from rest_framework.status import HTTP_404_NOT_FOUND, HTTP_200_OK, HTTP_400_BAD_REQUEST

from hostel_api.models import *
from hostel_api.serializers import StudentSerializer, RoomSerializer


@csrf_exempt
@api_view(["POST"])
@permission_classes((AllowAny,))
def login(request):
    username = request.data.get("username")
    password = request.data.get("password")
    if username is None or password is None:
        return Response({'response': 'Please provide both username and password'}, status=HTTP_200_OK)
    user = authenticate(username=username, password=password)

    if not user:
        context = {
            'response': 'Invalid Credentials',
        }
        return Response(context, status=HTTP_200_OK)
    refresh_token = RefreshToken.for_user(user=user)
    student = Student.objects.get(user_ptr_id=user.id)
    s = StudentSerializer(student, many=False)
    context = {
        'student': s.data,
        'response': 'Login Successful'
    }
    print()
    return Response(context,
                    status=HTTP_200_OK)


@csrf_exempt
@api_view(["POST"])
@permission_classes((AllowAny,))
def admin_login(request):
    username = request.data.get("username")
    password = request.data.get("password")
    if username is None or password is None:
        return Response({'error': 'Please provide both username and password'}, status=HTTP_400_BAD_REQUEST)
    user = authenticate(username=username, password=password)

    if not user:
        context = {
            'response': 'Invalid Credentials',
        }
        return Response(context, status=HTTP_404_NOT_FOUND)
    refresh_token = RefreshToken.for_user(user=user)
    context = {
        'token': str(refresh_token.access_token),
        'id': user.id,
        'username': username,
        'email': user.email,
        'first_name': user.first_name,
        'last_name': user.last_name,
        'password': user.password
    }
    return Response(context,
                    status=HTTP_200_OK)


@csrf_exempt
@api_view(["POST"])
@permission_classes((AllowAny,))
def signup2(request):
    username = request.data.get("username", "")
    password = request.data.get("password", "")
    email = request.data.get("email", "")
    first_name = request.data.get("first_name", "")
    last_name = request.data.get("last_name", "")
    gender = request.data.get("gender", "")
    if not username and not password and not email and not first_name and not last_name:
        return Response(
            data={
                "response": "username, password and email is required to register a user"
            },
            status=status.HTTP_400_BAD_REQUEST
        )
    new_user = Student.objects.create(
        username=username,
        password=make_password(password),
        email=email,
        first_name=first_name,
        last_name=last_name,
        is_staff=True,
        gender=gender
    )
    context = {
        'response': 'User Created Successfully'
    }
    return Response(context, status=status.HTTP_201_CREATED)


@csrf_exempt
@api_view(["POST"])
@permission_classes((AllowAny,))
def get_student_profile(request, student_id):
    student = Student.objects.get(id=student_id)

    context = {
        'first_name': student.first_name,
        'last_name': student.last_name,
        'hobbies': student.hobbies,
        'religion': student.religion,
        'school': student.school,
        'phone_number': student.phone_number,
        'sports': student.sports,
        'email': student.email,
        'course': student.course,
        'drugs': student.drugs,
        'visitor_count': student.visitor_count
    }
    return Response(context, status=status.HTTP_200_OK)


@csrf_exempt
@api_view(["POST"])
@permission_classes((AllowAny,))
def update_student_profile(request, student_id):
    first_name = request.data.get("first_name", "")
    last_name = request.data.get("last_name", "")
    hobbies = request.data.get("hobbies", "")
    religion = request.data.get("religion", "")
    phone_number = request.data.get("phone_number", "")
    sports = request.data.get("sports", "")
    email = request.data.get("email", "")
    course = request.data.get("course", "")
    drugs = request.data.get("drugs", "")
    visitor_count = request.data.get("visitor_count", "")
    if not first_name and not last_name and not hobbies and not religion and not phone_number and not sports:
        return Response(
            data={
                "response": "Some fields are missing"
            },
            status=status.HTTP_400_BAD_REQUEST
        )
    else:
        student = Student.objects.get(id=student_id)
        student.email = email
        student.first_name = first_name
        student.last_name = last_name
        student.hobbies = hobbies
        student.religion = religion
        student.phone_number = phone_number
        student.sports = sports
        student.drugs = drugs
        student.course = course
        student.visitor_count = visitor_count
        student.save()
        context = {
            'first_name': student.first_name,
            'last_name': student.last_name,
            'hobbies': student.hobbies,
            'religion': student.religion,
            'school': student.school,
            'phone_number': student.phone_number,
            'sports': student.sports,
            'email': student.email,
        }
        return Response(context, status=status.HTTP_201_CREATED)


@csrf_exempt
@api_view(["POST"])
@permission_classes((AllowAny,))
def get_students_with_similar_profile(request, student_id):
    student = Student.objects.get(id=student_id)
    students = Student.objects.filter(hobbies__contains=student.hobbies, religion=student.religion,
                                      sports=student.sports, school=student.school)
    s = StudentSerializer(students, many=True)
    return Response(s.data, status=status.HTTP_201_CREATED)


@csrf_exempt
@api_view(["POST"])
@permission_classes((AllowAny,))
def admin_add_room(request):
    room_number = request.data.get("room_number", "")
    type = request.data.get("room_type", "")
    short_description = request.data.get("room_description", "")
    floor = request.data.get("room_floor", "")
    if not room_number and not type and not short_description and not floor:
        return Response(
            data={
                "message": "Room details missing some data"
            },
            status=status.HTTP_400_BAD_REQUEST
        )
    else:
        room = Room.objects.create(
            room_number=room_number,
            type=type,
            short_description=short_description,
            floor=floor
        )
        context = {
            'room_floor': room.floor,
            'room_number': room.room_number,
            'short_descrption': room.short_description,
            'type': type
        }
        return Response(context, status=status.HTTP_201_CREATED)


@csrf_exempt
@api_view(["GET"])
@permission_classes((AllowAny,))
def admin_get_rooms(request):
    rooms = Room.objects.all()
    s = RoomSerializer(rooms, many=True)
    return Response(s.data, status=status.HTTP_201_CREATED)


@csrf_exempt
@api_view(["POST"])
@permission_classes((AllowAny,))
def admin_room_details(request, room_id):
    room = Room.objects.filter(id=room_id).first()
    s = RoomSerializer(room, many=False)
    context = {
        'response': 'Room Details',
        'room': s.data
    }
    return Response(context, status=status.HTTP_200_OK)


@csrf_exempt
@api_view(["GET"])
@permission_classes((AllowAny,))
def students_get_rooms(request):
    rooms = Room.objects.all().exclude(status_occupied='Occupied').exclude(status_occupied='Partial')
    # rooms = Room.objects.all()
    s = RoomSerializer(rooms, many=True)
    return Response(s.data, status=status.HTTP_201_CREATED)


@csrf_exempt
@api_view(["POST"])
@permission_classes((AllowAny,))
def student_room_booking(request, student_id, room_id):
    # pprint(request.body)
    if StudentRoom.objects.filter(student_id=student_id).count() > 0:
        context = {
            'response': 'You are already assigned a room, please file a complaint if you have one'
        }
        return Response(context, status=status.HTTP_200_OK)
    else:
        new_list = []
        room = Room.objects.filter(id=room_id).first()
        roommate_ids = request.POST['roommate_ids']

        roommate_list = roommate_ids.replace('%', '')
        chars = roommate_list.split('[', 1)[1]
        c = chars.split(']', 1)[0]
        print(eval(c))
        # for r in c.replace(",", ''):
        #     if r != "'" or r != "[" or r != "]":
        #         print(r)
                # new_list.append(r)
        # r = roommate_list.split()
        # print(r)
        if room.status_occupied == 'Partial':
            student_in_room = StudentRoom.objects.filter(room_id=room.id).first()
            StudentSelections.objects.create(
                student_id=student_id,
                possible_roommate_id=student_in_room.student_id,
                pairing_status='Pending'
            )
            StudentBooking.objects.create(
                student_id=student_id,
                room_id=room_id,
                approvement_status=False
            )
            StudentRoom.objects.create(
                student_id=student_id,
                room_id=room_id
            )
            room.status_occupied = 'Occupied'
            room.save()
            context = {
                'response': 'booking complete'
            }
            return Response(context, status=status.HTTP_201_CREATED)
        elif room.status_occupied == '' or room.status_occupied is None:
            StudentBooking.objects.create(
                student_id=student_id,
                room_id=room_id,
                approvement_status=True
            )
            StudentRoom.objects.create(
                student_id=student_id,
                room=room
            )
            room.status_occupied = 'Partial'
            room.save()
            if c.__contains__(','):
                for id in eval(c):
                    print(id)
                    StudentSelections.objects.create(
                        student_id=student_id,
                        possible_roommate_id=id,
                        pairing_status='Pending'
                    )
            else:
                StudentSelections.objects.create(
                    student_id=student_id,
                    possible_roommate_id=c,
                    pairing_status='Pending'
                )
            context = {
                'response': 'done'
            }
            return Response(context, status=status.HTTP_201_CREATED)


@csrf_exempt
@api_view(["POST"])
@permission_classes((AllowAny,))
def get_room_details(request, room_id, student_id):
    student = Student.objects.get(id=student_id)
    room = Room.objects.filter(id=room_id).first()
    ds = StudentRoom.objects.all()
    ids = []
    for s_room in ds:
        ids.append(s_room.student_id)

    students = Student.objects.filter(gender=student.gender).exclude(id__in=set(ids)).exclude(id=student_id)

    s = RoomSerializer(room, many=False)
    d = StudentSerializer(students, many=True)
    pprint(d.data)
    context = {
        'room': s.data,
        'students': d.data
    }
    return Response(context, status=status.HTTP_201_CREATED)


@csrf_exempt
@api_view(["POST"])
@permission_classes((AllowAny,))
def get_invites(request, student_id):
    invites = StudentSelections.objects.filter(pairing_status='Pending', possible_roommate_id=student_id)
    # print(invites)
    if StudentRoom.objects.filter(student_id=student_id).count() < 1:
        invites = StudentSelections.objects.filter(pairing_status='Pending', possible_roommate_id=student_id).exclude(student_id=student_id)
        ids = []
        for invite in invites:
            ids.append(invite.student_id)

        students = Student.objects.filter(id__in=ids)

        if students.count() > 0:
            s = StudentSerializer(students, many=True)
            context = {
                'invites': s.data,
                'response': 'Invites'
            }
            return Response(context, status=status.HTTP_201_CREATED)
        else:
            context = {
                'response': 'You have no invites'
            }
            return Response(context, status=status.HTTP_201_CREATED)
    else:
        context = {
            'response': 'A room is already assigned to you'
        }
        print('we at')
        return Response(context, status=status.HTTP_201_CREATED)


@csrf_exempt
@api_view(["POST"])
@permission_classes((AllowAny,))
def invite_details(request, student_id, invite_id, ):
    invited_by = Student.objects.filter(id=invite_id).first()
    booking = StudentBooking.objects.filter(student_id=invite_id, approvement_status=True).first()
    if StudentBooking.objects.filter(student_id=student_id, approvement_status=True).count() > 0:
        if booking is not None:
            room = Room.objects.filter(id=booking.room_id).first()
            s = RoomSerializer(room, many=False)
            d = StudentSerializer(invited_by, many=False)
            context = {
                'student': d.data,
                'room': s.data,
                'response': 'Invite Details'
            }
            return Response(context, status=status.HTTP_201_CREATED)
        else:
            context = {
                'message': 'That student\'s booking has not been approved'
            }
            return Response(context, status=status.HTTP_200_OK)
    else:
        context = {

        }


@csrf_exempt
@api_view(["POST"])
@permission_classes((AllowAny,))
def accept_invite(request, student_id, invite_id):
    if StudentRoom.objects.filter(student_id=student_id).count() > 0:
        context = {
            'response': 'A room is already assigned to you'
        }
        return Response(context, status=status.HTTP_200_OK)
    elif StudentSelections.objects.filter(pairing_status=True, student_id=invite_id):
        context = {
            'response': 'Sorry you cannot accept this invite'
        }
        return Response(context, status=status.HTTP_200_OK)
    else:
        booking = StudentBooking.objects.filter(student_id=invite_id).first()
        print(booking.id)
        booking.approvement_status = True
        booking.save()
        room = Room.objects.filter(id=booking.room_id).first()
        room.status_occupied = 'Occupied'
        room.save()
        selection = StudentSelections.objects.filter(student_id=invite_id, possible_roommate_id=student_id).first()
        selection.pairing_status = 'Complete'
        selection.save()
        StudentRoom.objects.create(
            room=room,
            student_id=student_id,
        )
        StudentBooking.objects.create(
            student_id=student_id,
            room_id=room.id,
            approvement_status=True
        )
        context = {
            'response': 'Pairing Complete'
        }
        return Response(context, status=status.HTTP_201_CREATED)


@csrf_exempt
@api_view(["GET"])
@permission_classes((AllowAny,))
def admin_get_bookings(request):
    bookings = StudentBooking.objects.filter(approvement_status=False)
    if bookings.count() < 1:
        context = {
            'response': 'Currently No Bookings'
        }
        return Response(context, status=status.HTTP_201_CREATED)
    else:
        room_ids = []
        for booking in bookings:
            room_ids.append(booking.room_id)
        rooms = Room.objects.filter(id__in=room_ids)
        s = RoomSerializer(rooms, many=True)
        context = {
            'response': 'Bookings',
            'rooms': s.data
        }
        pprint(s.data)
        return Response(context,status=status.HTTP_200_OK)


@csrf_exempt
@api_view(["POST"])
@permission_classes((AllowAny,))
def get_booking_details(request, room_id):
    room = Room.objects.get(id=room_id)
    bookings = StudentBooking.objects.filter(room_id=room_id)
    ids = []
    for booking in bookings:
        ids.append(booking.student.id)
    students = Student.objects.filter(id__in=ids)
    s = StudentSerializer(students, many=True)
    d = RoomSerializer(room, many=False)

    context = {
        'bookings': s.data,
        'room': d.data,
        'response': 'Booking Details'
    }
    pprint(context)
    return Response(context, status=status.HTTP_200_OK)


@csrf_exempt
@api_view(["POST"])
@permission_classes((AllowAny,))
def student_room(request, student_id):
    if StudentRoom.objects.filter(student_id=student_id).count() > 0:
        student = StudentRoom.objects.filter(student_id=student_id).first()
        room = Room.objects.filter(id=student.room.id).first()
        d = RoomSerializer(room, many=False)
        r_id = 0
        if StudentSelections.objects.filter(student_id=student_id, pairing_status='Complete').count() > 0:
            r_id = StudentSelections.objects.filter(student_id=student_id, pairing_status='Complete').first().possible_roommate_id
        elif StudentSelections.objects.filter(possible_roommate_id=student_id, pairing_status='Complete').count() > 0:
            r_id = StudentSelections.objects.filter(possible_roommate_id=student_id, pairing_status='Complete').first().student_id
        if r_id != 0:
            roommate = Student.objects.filter(id=r_id).first()
            roommate_room = StudentRoom.objects.filter(student_id=roommate.id).first()
            if roommate_room is not None:
                s = StudentSerializer(roommate, many=False)
                context = {
                    'room': d.data,
                    'roommate': s.data,
                    'response': 'Assigned Residence Details'
                }
                return Response(context, status=status.HTTP_200_OK)
            else:
                context = {
                    'room': d.data,
                    'response': 'Your Roommate Vacated'
                }
                return Response(context, status=status.HTTP_200_OK)
        else:
            context = {
                'response': 'No roommate assigned to you yet',
                'room': d.data,
            }
            return Response(context, status=status.HTTP_200_OK)
    else:
        context = {
            'response': 'You are not assign a room yet',
            'room': 'no room',
            'roommate': 'no data',
        }
        print('here')
        return Response(context, status=status.HTTP_200_OK)


consumer_key = 'DPiD4iVF57srI7A7Ol2kCBrv0yHmIIxG'
consumer_secret = '04ib4s45v0EGwoD4'


def get_access_token():
    api_url = "https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials"
    response = requests.get(api_url, auth=HTTPBasicAuth(consumer_key, consumer_secret))
    access_token = json.loads(response.text)
    return access_token['access_token']


@csrf_exempt
@api_view(["POST"])
@permission_classes((AllowAny,))
def initiate_stk_push(request, student_id, room_id):
    if MpesaPayment.objects.filter(student_id=student_id, room_id=room_id).count() > 0:
        context = {
            'message': 'You already payed for this room'
        }
        return Response(context, status=status.HTTP_200_OK)
    else:
        access_token = get_access_token()
        timestamp = datetime.now().strftime('%Y%m%d%H%M%S')
        short_code = "174379"
        passkey = 'bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919'
        data_to_encode = short_code + passkey + timestamp
        online_password = base64.b64encode(data_to_encode.encode())
        password = online_password.decode('utf-8')

        MpesaPayment.objects.create(
            student_id=student_id,
            room_id=room_id,
            status_payed=True
        )
        api_url = "https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest"
        headers = {"Authorization": "Bearer %s" % access_token}
        request = {
            "BusinessShortCode": "174379",
            "Password": password,
            "Timestamp": timestamp,
            "TransactionType": "CustomerPayBillOnline",
            "Amount": "1",
            "PartyA": "254704976963",
            "PartyB": "174379",
            "PhoneNumber": "254704976963",
            "CallBackURL": "https://8cc770f1.ngrok.io/api/callback",
            "AccountReference": "SAMRISE",
            "TransactionDesc": "You are about to pay"
        }
        response = requests.post(api_url, json=request, headers=headers)
        print(response.text)
        context = {
            'message': 'Payment Successful'
        }
        return JsonResponse(context, safe=False)




def student_get_room_booked(request, student_id):
    bookings = StudentBooking.objects.filter(student_id=student_id, approvement_status=False)
    if bookings.count() < 1:
        context = {
            'response': 'Currently No Bookings'
        }
        return Response(context, status=status.HTTP_201_CREATED)
    else:
        room_ids = []
        for booking in bookings:
            room_ids.append(booking.room_id)
        rooms = Room.objects.filter(id__in=room_ids)
        s = RoomSerializer(rooms, many=True)
        context = {
            'response': 'Bookings',
            'rooms': s.data
        }
        pprint(s.data)
        return Response(context, status=status.HTTP_200_OK)


@csrf_exempt
@api_view(["GET"])
@permission_classes((AllowAny,))
def student_room_invitees(request, student_id):
    if not StudentSelections.objects.filter(student_id=student_id, pairing_status='Complete').count() > 0:
        student_selections = StudentSelections.objects.filter(student_id=student_id)
        if student_selections.count() < 1:
            context = {
                'response': 'Currently No Bookings'
            }
            return Response(context, status=status.HTTP_201_CREATED)
        else:
            student_ids = []
            for student_selection in student_selections:
                student_ids.append(student_selection.possible_roommate_id)
            students = Student.objects.filter(id__in=student_ids)
            s = StudentSerializer(students, many=True)
            context = {
                'students': s.data
            }
            pprint(s.data)
            return Response(s.data, status=status.HTTP_200_OK)


@csrf_exempt
@api_view(["GET"])
@permission_classes((AllowAny,))
def uninvite_student(request, student_id, possible_roommate_id):
    student_selection = StudentSelections.objects.filter(student_id=student_id, possible_roommate_id=possible_roommate_id).first()
    if student_selection is not None:
        student_selection.delete()
        context = {
            'message': 'Invite Revoked',
        }
        return Response(context, status=status.HTTP_200_OK)
    else:
        context = {
            'message': 'Invite Not Found',
        }
        return Response(context, status=status.HTTP_200_OK)


@csrf_exempt
@api_view(["GET"])
@permission_classes((AllowAny,))
def vacate_room(request, student_id):
    student_room = StudentRoom.objects.filter(student_id=student_id).first()

    if student_room is not None:
        student_room.delete()
        if StudentRoom.objects.filter(room_id=student_room.room_id).count() > 0:
            Room.objects.filter(id=student_room.room_id).update(
                status_occupied='Partial'
            )
        else:
            Room.objects.filter(id=student_room.room_id).update(
                status_occupied=''
            )
            context = {
                'message': 'Vacation successfull',
            }
            return Response(context, status=status.HTTP_200_OK)
    else:
        context = {
            'message': 'No Room Assigned To You Yet',
        }
        return Response(context, status=status.HTTP_200_OK)


# def accept_invite(request):
    # return Response(context, status=status.HTTP_201_CREATED)
# @csrf_exempt
# @api_view(["POST"])
# @permission_classes((AllowAny,))
# def book_room(request, student_id, room_id):
#     room = Room.objects.filter(id=room_id).first()
#     if room.status_occupied == 'Partial':
#         StudentBooking.objects.create(
#             student_id=student_id,
#             room_id=room.id,
#             approvement_status='Approved'
#         )
#         room.status_occupied = 'Occupied'
#         room.save()
#     elif not room.status_occupied or room.status_occupied == '':
#         if StudentBooking.objects.filter(room_id=room_id).count() < 1:
#             StudentBooking.objects.create(
#                 student_id=student_id,
#                 room_id=room.id,
#                 approvement_status='Approved'
#             )
#             room.status_occupied = 'Partial'
#             room.save()
#             StudentSelections.objects.create(
#
#             )


