from django.conf import settings
from django.db import models


# Create your models here.
class Genre(models.Model):
    name = models.CharField(max_length=50)

    def __str__(self):
        return self.name


class Movie(models.Model):
    genre_ids = models.ManyToManyField(Genre, related_name='movies')

    backdrop_path = models.CharField(max_length=200, blank=True, null=True)
    poster_path = models.CharField(max_length=200, blank=True, null=True)
    overview = models.TextField(null=True, blank=True)
    release_date = models.DateField(null=True, blank=True)
    original_title = models.CharField(max_length=100)
    title = models.CharField(max_length=100)
    popularity = models.FloatField(null=True, blank=True)
    vote_count = models.IntegerField(null=True, blank=True)
    vote_average = models.FloatField(null=True, blank=True)

    # 빅데이터 머신러닝 프로세스요소
    movie_reference_overview = models.JSONField(null=True, blank=True)
    def __str__(self):
        return self.title

  
# 평가
class Article(models.Model):
    like_users = models.ManyToManyField(settings.AUTH_USER_MODEL, related_name='like_articles')

    movie = models.ForeignKey(Movie, on_delete=models.CASCADE, related_name='articles')
    user = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE, related_name='articles')
    rate = models.FloatField(null=True, blank=True)
    title = models.CharField(max_length=200)
    content = models.TextField()
    points = models.PositiveIntegerField(null=True, blank=True, default=0)  # 현재 방식 : get 시점에 1000*좋아요 수 + 후원금액
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    def __str__(self):
        return self.title


# 댓글
class Comment(models.Model):
    #id = models.BigAutoField(primary_key=True)

    user = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE, related_name='comments')
    article = models.ForeignKey(Article, on_delete=models.CASCADE, related_name='comments')
    content = models.TextField(max_length=50)
    thanks_content = models.TextField(null=True, blank=True, max_length=50)
    mileage = models.PositiveIntegerField(null=True, blank=True, default=0)  # 댓글 작성시 후원 금액
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    def __str__(self):
        return self.content



# 좋아요 한 평가들
class Likes(models.Model):
    user = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE, related_name='my_articles')
    article = models.ForeignKey(Article, on_delete=models.CASCADE, related_name='bookmark_users')
    title = models.CharField(max_length=100)
    backdrop_path = models.TextField()
    
    def __str__(self):
        return self.title
