from django.contrib import admin

# Register your models here.admin_add_room
from hostel_api.models import *

admin.site.register(Room)
admin.site.register(StudentRoom)
admin.site.register(StudentSelections)
admin.site.register(StudentBooking)
admin.site.register(Student)
# admin.site.register(Student)
# admin.site.register(StudentSelections)
