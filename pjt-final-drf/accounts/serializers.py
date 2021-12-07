from rest_framework import serializers
from django.contrib.auth import get_user_model
from .models import Curator


class UserSerializer(serializers.ModelSerializer):
    password = serializers.CharField(write_only=True)
    
    class Meta:
        model = get_user_model()
        fields = ('username', 'nickname','password',)

class UserProfileSerializer(serializers.ModelSerializer):
    following = serializers.SerializerMethodField()
    followers = serializers.SerializerMethodField()
    comments_count = serializers.IntegerField(source='comments.count', read_only=True)
    articles_count = serializers.IntegerField(source='articles.count', read_only=True)

    image = serializers.ImageField(use_url=True)
    class Meta:
        model = get_user_model()
        fields = ('id', 'username', 'nickname', 'mileage', 'image', 'introduction', 'following', 'followers', 'articles_count', 'comments_count')

    def get_following(self, obj):
        return FollowingSerializer(obj.following.all(), many=True).data

    def get_followers(self, obj):
        return FollowersSerializer(obj.followers.all(), many=True).data

class UserProfileImageSerializer(serializers.ModelSerializer):
    image = serializers.ImageField(use_url=True)
    class Meta:
        model = get_user_model()
        fields = ('nickname', 'image', 'introduction')

class UserProfileSimpleSerializer(serializers.ModelSerializer):
    class Meta:
        model = get_user_model()
        fields = ('nickname', 'introduction')

class UserProfileMileageSerializer(serializers.ModelSerializer):
    class Meta:
        model = get_user_model()
        fields = ('mileage',)

class UserCuratorSerializer(serializers.ModelSerializer):
    image = serializers.ImageField(use_url=True)
    class Meta:
        model = get_user_model()
        fields = ('id', 'username', 'nickname', 'exp', 'image', 'introduction',)

# 도네이션시 갱신
class FollowCuratorSerializer(serializers.ModelSerializer):
    class Meta:
        model = Curator
        fields = ('id', 'to_user','from_user', 'score', )

# 도네이션 순위 출력용
class LikeCuratorSerializer(serializers.ModelSerializer):
    to_user = UserProfileSerializer(read_only=True)
    class Meta:
        model = Curator
        fields = ('id', 'to_user','from_user', 'score', )

# 후원자 수 카운트 용
class FollowersSerializer(serializers.ModelSerializer):
    class Meta:
        model = Curator
        fields = ('id', 'from_user', 'score', )

# 후원하는 큐레이터 카운트용
class FollowingSerializer(serializers.ModelSerializer):
    class Meta:
        model = Curator
        fields = ('id', 'to_user', 'score', )