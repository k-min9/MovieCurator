from rest_framework import serializers
from .models import Movie, Article, Comment, Likes
from django.contrib.auth import get_user_model

from accounts.serializers import UserProfileSerializer

class MovieSerializer(serializers.ModelSerializer):
    class Meta:
        model = Movie
        fields = '__all__'
        read_only_fields = ('genres',)

class CommentSerializer(serializers.ModelSerializer):
    user = UserProfileSerializer(read_only=True)
    class Meta:
        model = Comment
        fields = ('id', 'user', 'article', 'content', 'created_at', 'updated_at',)
        read_only_fields = ('article',)

class CommentDonateSerializer(serializers.ModelSerializer):
    user = UserProfileSerializer(read_only=True)
    class Meta:
        model = Comment
        fields = ('id', 'user', 'article', 'content', 'mileage', 'thanks_content', 'created_at', 'updated_at',)
        read_only_fields = ('article',)

class ArticleSerializer(serializers.ModelSerializer):
    comments = CommentSerializer(many=True, read_only=True)
    comments_count = serializers.IntegerField(source='comments.count', read_only=True)
    user = UserProfileSerializer(read_only=True)
    movie = MovieSerializer(read_only=True)
    class Meta:
        model = Article
        fields = ('id', 'movie', 'user', 'rate', 'title', 'content', 'created_at', 'updated_at', 'comments', 'comments_count')

class ArticleSimpleSerializer(serializers.ModelSerializer):
    class Meta:
        model = Article
        fields = '__all__'


class ArticleUpdateSerializer(serializers.ModelSerializer):
    class Meta:
        model = Article
        fields = ('id','rate','content','title')    


class ArticlePointSerializer(serializers.ModelSerializer):
    class Meta:
        model = Article
        fields = ('points',)
        
        
class LikesSerializer(serializers.ModelSerializer):
    article = ArticleSerializer(read_only=True)
    class Meta:
        model = Likes
        fields = ('id', 'user', 'article', 'title', 'backdrop_path',)
        read_only_fields = ('user', 'article', 'title', 'backdrop_path',)

class UserCuratorSerializer(serializers.ModelSerializer):
    image = serializers.ImageField(use_url=True)
    article = ArticleSimpleSerializer(read_only=True)
    class Meta:
        model = get_user_model()
        fields = ('id', 'username', 'nickname', 'mileage', 'image', 'introduction', 'article')
