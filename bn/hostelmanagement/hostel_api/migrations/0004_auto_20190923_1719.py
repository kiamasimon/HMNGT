# Generated by Django 2.2.5 on 2019-09-23 17:19

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('hostel_api', '0003_auto_20190923_1654'),
    ]

    operations = [
        migrations.AddField(
            model_name='room',
            name='floor',
            field=models.CharField(max_length=250, null=True),
        ),
        migrations.AlterField(
            model_name='room',
            name='status_occupied',
            field=models.CharField(max_length=250, null=True),
        ),
    ]