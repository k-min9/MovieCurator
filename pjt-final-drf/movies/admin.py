from django.contrib import admin
from .models import Genre, Movie, Article, Comment, Likes

admin.site.register(Movie)
admin.site.register(Genre)
admin.site.register(Article)
admin.site.register(Comment)
admin.site.register(Likes)