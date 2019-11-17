# Generated by Django 2.2.5 on 2019-10-13 15:32

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('hostel_api', '0005_student_settled'),
    ]

    operations = [
        migrations.CreateModel(
            name='MpesaPayment',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('status_payed', models.BooleanField(default=False)),
                ('room', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='hostel_api.Room')),
                ('student', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='hostel_api.Student')),
            ],
        ),
    ]
