from django.urls import path

from hostel_api import views

app_name = 'Hostel'
urlpatterns = [
    path('signup', views.signup2),
    path('signin', views.login),
    path('get_students_with_similar_profile/<int:student_id>', views.get_students_with_similar_profile),
    path('update_student_profile/<int:student_id>', views.update_student_profile),
    path('get_student_profile/<int:student_id>', views.get_student_profile),
    path('admin_add_room', views.admin_add_room),
    path('admin_get_rooms', views.admin_get_rooms),
    path('admin_room_details/<int:room_id>', views.admin_room_details),
    path('students_get_rooms', views.students_get_rooms),
    path('admin_login', views.admin_login),
    path('student_room_booking/<int:student_id>/<int:room_id>', views.student_room_booking),
    path('get_room_details/<int:room_id>/<int:student_id>', views.get_room_details),
    path('get_invites/<int:student_id>', views.get_invites),
    path('invite_details/<int:student_id>/<int:invite_id>', views.invite_details),
    path('accept_invite/<int:student_id>/<int:invite_id>', views.accept_invite),
    path('admin_get_bookings', views.admin_get_bookings),
    path('get_booking_details/<int:room_id>', views.get_booking_details),
    path('student_room/<int:student_id>', views.student_room),
    path('initiate_stk_push/<int:student_id>/<int:room_id>', views.initiate_stk_push),

    path('student/vacate/<int:student_id>', views.vacate_room),
    path('student_room_invitees/<int:student_id>', views.student_room_invitees),
    path('uninvite_student/<int:student_id>/<int:possible_roommate_id>', views.uninvite_student),
]