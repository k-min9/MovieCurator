from django.shortcuts import get_object_or_404
from rest_framework import status
from rest_framework.decorators import api_view, permission_classes, authentication_classes
from rest_framework.response import Response
from rest_framework.permissions import AllowAny
from .models import Curator
from .serializers import UserSerializer, UserProfileSerializer, UserProfileImageSerializer, UserProfileSimpleSerializer, UserProfileMileageSerializer, UserCuratorSerializer, FollowCuratorSerializer, LikeCuratorSerializer
from django.db.models import Q

from rest_framework.permissions import IsAuthenticated
from rest_framework_jwt.authentication import JSONWebTokenAuthentication

from django.contrib.auth import get_user_model


@api_view(['POST'])
@permission_classes([AllowAny])
def signup(request):
	# Client에서 온 데이터를 받아서
    User = get_user_model()
    password = request.data.get('password')
    password_confirmation = request.data.get('passwordConfirmation')
		
	# 패스워드 일치 여부 체크
    if password != password_confirmation:
        return Response({'error': '비밀번호가 일치하지 않습니다.'}, status=status.HTTP_400_BAD_REQUEST)
        
    # 이미 존재하는 아이디인지 체크 (닉네임은 겹쳐도 괜찮음)
    if User.objects.filter(username=request.data.get('username')).exists():
        return Response({'error': '일치하는 아이디가 존재합니다.'}, status=status.HTTP_400_BAD_REQUEST)
		
	# UserSerializer
    serializer = UserSerializer(data=request.data)
    if serializer.is_valid(raise_exception=True):
        user = serializer.save()
        user.set_password(request.data.get('password'))  # 비밀번호 해상 
        user.save()
        # password는 표현(response)할 때 보내지 않기(write_only)
        return Response(serializer.data, status=status.HTTP_201_CREATED)
    return Response({'error': '유효하지 않은 요청입니다.'}, status=status.HTTP_400_BAD_REQUEST)

@api_view(['GET', 'PUT', 'DELETE'])
@authentication_classes([JSONWebTokenAuthentication])
@permission_classes([IsAuthenticated])
def my_profile_detail(request):
    if request.method == 'GET':
        serializer = UserProfileSerializer(request.user)
        return Response(serializer.data, status=status.HTTP_200_OK)
    elif request.method == 'PUT':
        image = request.data.get('image')
        # 이미지 여부에 따라 시리얼라이저를 바꾼다.
        if image:    
            serializer = UserProfileImageSerializer(request.user, data=request.data)
        else:
            serializer = UserProfileSimpleSerializer(request.user, data=request.data)
        if serializer.is_valid(raise_exception=True):
            if image:
                serializer.save(image=image)
            else:
                serializer.save()
            return Response(serializer.data, status=status.HTTP_200_OK)
    elif request.method == 'DELETE':
        user_pk = request.user.pk
        request.user.delete()
        return Response({ 'delete': f'{user_pk}번 회원이 탈퇴했습니다.' }, status=status.HTTP_204_NO_CONTENT)


@api_view(['GET', 'PUT', 'DELETE'])
# @authentication_classes([JSONWebTokenAuthentication])
# @permission_classes([IsAuthenticated])
@permission_classes([AllowAny])
def profile_detail(request, user_pk):
    user = get_object_or_404(get_user_model(), pk=user_pk)
    if request.method == 'GET':
        serializer = UserProfileSerializer(user)
        return Response(serializer.data)
    if request.user == user.user:
        if request.method == 'PUT':
            image = request.data.get('image')
            serializer = UserProfileSerializer(user, data=request.data)
            if serializer.is_valid(raise_exception=True):
                serializer.save(image=image)
                return Response(serializer.data)
        elif request.method == 'DELETE':
            user_pk = request.user.pk
            request.user.delete()
            return Response({ 'delete': f'{user_pk}번 회원이 탈퇴했습니다.' }, status=status.HTTP_204_NO_CONTENT)
    return Response({ 'Unauthorized': '본인이 아닙니다.'}, status=status.HTTP_403_FORBIDDEN)

@api_view(['GET'])
# @authentication_classes([JSONWebTokenAuthentication])
# @permission_classes([IsAuthenticated])
@permission_classes([AllowAny])
def get_user_info(request, username):
    user = get_object_or_404(get_user_model(), username=username)
    serializer = UserProfileSerializer(user)
    return Response(serializer.data, status=status.HTTP_200_OK)


# 마일리지 충전
@api_view(['PUT'])
@authentication_classes([JSONWebTokenAuthentication])
@permission_classes([IsAuthenticated])
def mileage_change(request):
    mileage = request.user.mileage + request.data.get('mileage')
    data = {
        'mileage':mileage
    }
    serializer = UserProfileMileageSerializer(request.user, data=data)
    if serializer.is_valid(raise_exception=True):
        serializer.save()
        return Response(serializer.data, status=status.HTTP_200_OK)

# 후원 받기 (유저)
@api_view(['PUT'])
@authentication_classes([JSONWebTokenAuthentication])
@permission_classes([IsAuthenticated])
def donate(request, user_pk):
    #curators = Curator.objects.filter(user__pk=request.user.pk, article__pk=article_pk).first()
    # 후원 대상
    user = get_object_or_404(get_user_model(), pk=user_pk)
    # 충전 마일리지
    mileage = request.data.get('mileage')
    # 기존에 후원한 적 있나 체크
    curator = Curator.objects.filter(from_user_id__pk=request.user.pk, to_user_id__pk=user_pk).first()
    if curator:
        data = {
            'to_user' : user_pk,
            'from_user' : request.user.pk,
            'score' : curator.score + mileage
        }
        serializer = FollowCuratorSerializer(curator, data=data)
        if serializer.is_valid(raise_exception=True):
            serializer.save()
    else:
        data = {
            'to_user' : user_pk,
            'from_user' : request.user.pk,
            'score' : mileage
        }
        serializer = FollowCuratorSerializer(data=data)
        if serializer.is_valid(raise_exception=True):
            serializer.save()

    # 기존 마일리지에 가산
    mileage = mileage + user.mileage
    data = {
        'mileage': mileage 
    }
    serializer = UserProfileMileageSerializer(user, data=data)
    if serializer.is_valid(raise_exception=True):
        serializer.save()
        return Response(serializer.data, status=status.HTTP_200_OK)

# 큐레이터 이름으로 검색
@api_view(['GET'])
# @authentication_classes([JSONWebTokenAuthentication])
# @permission_classes([IsAuthenticated])
@permission_classes([AllowAny])
def curator_search(request):
    User = get_user_model()
    searchKeyword = request.GET.get('searchKeyword')
    # 이름이나 닉네임이 사용자의 입력를 포함하는 유저들을 반환 (6명 정도)
    users = User.objects.filter(Q(nickname__icontains=searchKeyword)|Q(username__icontains=searchKeyword))[:6]
    serializer = UserCuratorSerializer(users, many=True)
    return Response(serializer.data, status=status.HTTP_200_OK)

# 큐레이터 번호로 검색
@api_view(['GET'])
# @authentication_classes([JSONWebTokenAuthentication])
# @permission_classes([IsAuthenticated])
@permission_classes([AllowAny])
def curator_detail(request, user_pk):
    user = get_object_or_404(get_user_model(), pk=user_pk)
    serializer = UserCuratorSerializer(user)
    return Response(serializer.data, status=status.HTTP_200_OK)


# 후원한 큐레이터를 가져오기
@api_view(['GET'])
@authentication_classes([JSONWebTokenAuthentication])
@permission_classes([IsAuthenticated])
def likes_list_curator(request):
    curators = Curator.objects.filter(from_user_id__pk=request.user.pk).order_by('-score')[:6]
    serializer = LikeCuratorSerializer(curators, many=True)
    return Response(serializer.data)