from django.db import models
from django.contrib.auth.models import AbstractUser
from imagekit.models import ProcessedImageField
from imagekit.processors import ResizeToFill
from django.contrib.auth import get_user_model

class User(AbstractUser):
    introduction = models.TextField(default='영화 애호가')
    nickname = models.CharField(max_length=20, blank=True)
    image = ProcessedImageField(
        null=True,
        blank=True,
        upload_to='profile/%Y/%m/%d/',
        processors=[ResizeToFill(200,200)],
        format='JPEG',
        options={'quality': 100}
        )
    mileage = models.PositiveIntegerField(default=0)
    exp = models.PositiveIntegerField(default=0)
    donate = models.ManyToManyField('self', through='Curator', symmetrical=False)

class Curator(models.Model):
    from_user = models.ForeignKey(get_user_model(), on_delete=models.CASCADE, related_name='following')
    to_user = models.ForeignKey(get_user_model(), on_delete=models.CASCADE, related_name='followers')
    score = models.PositiveIntegerField(default=0, db_index=True)

    def __str__(self):
        return self.to_user


    class Meta:
        constraints = [models.UniqueConstraint(fields=['from_user','to_user'],  name="unique_followers")]