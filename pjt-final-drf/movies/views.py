from django.shortcuts import get_object_or_404
from django.conf import settings
from django.db.models import Q

from rest_framework import serializers, status
from rest_framework.response import Response
from rest_framework.decorators import api_view, authentication_classes, permission_classes
from rest_framework.permissions import IsAuthenticated
from rest_framework.permissions import AllowAny
from rest_framework_jwt.authentication import JSONWebTokenAuthentication
from .models import Movie, Article, Comment, Likes
from .serializers import MovieSerializer, ArticleSerializer, CommentSerializer, CommentDonateSerializer, ArticlePointSerializer, ArticleUpdateSerializer, LikesSerializer, UserCuratorSerializer

# user 모델 가져오기
from django.contrib.auth import get_user_model

# 이번 주 내에 적힌 평가 필터용
from datetime import datetime, timedelta



# 영화 단일 데이터
@api_view(['GET'])
# @authentication_classes([JSONWebTokenAuthentication])
# @permission_classes([IsAuthenticated])
@permission_classes([AllowAny])
def movie_detail(request, movie_pk):
    movie = get_object_or_404(Movie, pk=movie_pk)
    serializer = MovieSerializer(movie)
    return Response(serializer.data)

# 머신러닝 프로세스 기반 추천
@api_view(['GET'])
# @authentication_classes([JSONWebTokenAuthentication])
# @permission_classes([IsAuthenticated])
@permission_classes([AllowAny])
def movie_recommend(request, movie_pk):
    movie = get_object_or_404(Movie, pk=movie_pk)
    movies_recommend =[]
    for pk in movie.movie_reference_overview:
        answer = get_object_or_404(Movie, pk=pk)
        movies_recommend.append(answer)
    serializer = MovieSerializer(movies_recommend, many=True)
    return Response(serializer.data, status=status.HTTP_200_OK)

# 머신러닝 프로세스 기반 영화 추천
# 현재는 인기순으로 영화 제목 보내기 (selectBox)
@api_view(['GET'])
# @authentication_classes([JSONWebTokenAuthentication])
# @permission_classes([IsAuthenticated])
@permission_classes([AllowAny])
def home(request):
    # 선정 알고리즘은 동봉된 01.ML_recommend.py와 기술서 참조
    movies = Movie.objects.filter(pk__in=[588228, 508943, 438631, 566525, 436969, 550988, 522402, 497698, 451048, 459151, 370172, 482373])
    # 만약을 위한 대비코드
    # movies = Movie.objects.order_by('-popularity')[:12]

    serializer = MovieSerializer(movies, many=True)
    return Response(serializer.data, status=status.HTTP_200_OK)

# 영화 장르별/최신순/평점순/인기순
@api_view(['GET'])
# @authentication_classes([JSONWebTokenAuthentication]) # JWT가 유효한지
# @permission_classes([IsAuthenticated]) # 인증 여부를 확인
@permission_classes([AllowAny])
def movie_list(request):
    filters = request.GET.get('filter')
    # 최신순/평점순/인기순
    if filters in ('release_date', 'vote_average', 'popularity'):
        movies = Movie.objects.order_by(f'-{filters}')[:30]
    # 장르별
    else:
        movies = Movie.objects.filter(genre_ids__name=filters)
        movies = movies.order_by('-popularity')[:30]
        #movies = Movie.objects.filter(genre_ids__name=filters)[:30]
    serializer = MovieSerializer(movies, many=True)
    return Response(serializer.data)

# 영화 이름으로 검색
@api_view(['GET'])
# @authentication_classes([JSONWebTokenAuthentication])
# @permission_classes([IsAuthenticated])
@permission_classes([AllowAny])
def movie_search(request):
    searchKeyword = request.GET.get('searchKeyword')
    # 한글 제목이나 원본 제목이 사용자의 입력를 포함하는 영화들을 반환
    movies = Movie.objects.filter(Q(title__icontains=searchKeyword)|Q(original_title__icontains=searchKeyword))[:25]
    serializer = MovieSerializer(movies, many=True)
    return Response(serializer.data, status=status.HTTP_200_OK)


# 단일 평가 생성, 조회, 삭제, 수정
@api_view(['GET', 'POST', 'PUT', 'DELETE'])
@authentication_classes([JSONWebTokenAuthentication])
@permission_classes([IsAuthenticated])
def article_detail(request, movie_pk):
    # 평가 생성, 조회
    movie = get_object_or_404(Movie, pk=movie_pk)
    if request.method == 'GET':
        article = Article.objects.filter(user__pk=request.user.pk, movie__pk=movie_pk).first()
        serializer = ArticleSerializer(article)
        return Response(serializer.data, status=status.HTTP_200_OK)
    elif request.method == 'POST':
        serializer = ArticleSerializer(data=request.data)
        if serializer.is_valid(raise_exception=True):
            serializer.save(user=request.user, movie=movie)
            return Response(serializer.data, status=status.HTTP_201_CREATED)
            
    # 평가 삭제, 수정 (작성자인지 아닌지 체크)
    article = Article.objects.filter(user__pk=request.user.pk, movie__pk=movie_pk).first()
    articleId = request.data.get('id')
    if articleId == article.pk:
        # 평가 제거
        if request.method == 'DELETE':
            article.delete()
            data = {
                'delete' : '평가가 삭제되었습니다.'
            }
            return Response(data, status=status.HTTP_204_NO_CONTENT)
        # 평가 수정
        elif request.method == 'PUT':
            serializer = ArticleUpdateSerializer(article, data=request.data)
            if serializer.is_valid(raise_exception=True):
                serializer.save()
                return Response(serializer.data)
    return Response({ 'Unauthorized': '작성자가 아닙니다.'}, status=status.HTTP_403_FORBIDDEN)


# 평가 번호로 평가 내용 가져오기
@api_view(['GET'])
# @authentication_classes([JSONWebTokenAuthentication])
# @permission_classes([IsAuthenticated])
@permission_classes([AllowAny])
def get_article(request, article_pk):
    article = get_object_or_404(Article, pk=article_pk)
    serializer = ArticleSerializer(article)
    return Response(serializer.data, status=status.HTTP_200_OK)

# 해당 영화에 적힌 평가 다 가져오기
@api_view(['GET'])
# @authentication_classes([JSONWebTokenAuthentication])
# @permission_classes([IsAuthenticated])
@permission_classes([AllowAny])
def article_list(request, movie_pk):
    # 현재는 작성순이지만, 마일리지 추가로 알고리즘 변동
    articles = Article.objects.filter(movie__pk=movie_pk).order_by('-points','-pk')
    serializer = ArticleSerializer(articles, many=True)
    return Response(serializer.data, status=status.HTTP_200_OK)

# 해당 작성자가 적은 평가 최신 3개
@api_view(['GET'])
# @authentication_classes([JSONWebTokenAuthentication])
# @permission_classes([IsAuthenticated])
@permission_classes([AllowAny])
def article_curator(request, user_pk):
    articles = Article.objects.filter(user__pk=user_pk).order_by('-pk')[:3]
    serializer = ArticleSerializer(articles, many=True)
    return Response(serializer.data, status=status.HTTP_200_OK)

# 해당 작성자가 적은 평가(일단 전부)
@api_view(['GET'])
# @authentication_classes([JSONWebTokenAuthentication])
# @permission_classes([IsAuthenticated])
@permission_classes([AllowAny])
def article_curator_all(request, user_pk):
    articles = Article.objects.filter(user__pk=user_pk).order_by('-pk')
    serializer = ArticleSerializer(articles, many=True)
    return Response(serializer.data, status=status.HTTP_200_OK)


# 이번주에 적힌 모든 평가 중 상위 6개
@api_view(['GET'])
# @authentication_classes([JSONWebTokenAuthentication])
# @permission_classes([IsAuthenticated])
@permission_classes([AllowAny])
def article_home(request):
    # 필터 : 이번 주
    one_week_ago = datetime.today() - timedelta(days=7)

    articles = Article.objects.filter(created_at__gte=one_week_ago).order_by('-points')[:6]
    serializer = ArticleSerializer(articles, many=True)
    return Response(serializer.data, status=status.HTTP_200_OK)

# 단일 댓글 생성, 조회(?), 삭제, 수정
@api_view(['GET', 'POST', 'PUT', 'DELETE'])
@authentication_classes([JSONWebTokenAuthentication])
@permission_classes([IsAuthenticated])
def comment_detail(request, article_pk):
    # 댓글 생성, 조회
    article = get_object_or_404(Article, pk=article_pk)
    if request.method == 'GET':
        comment = Comment.objects.filter(user__pk=request.user.pk, article__pk=article_pk).first()
        serializer = CommentSerializer(comment)
        return Response(serializer.data, status=status.HTTP_200_OK)
    elif request.method == 'POST':
        # 도네이션 코멘트인지 아닌지에 따라 직렬화가 바뀐다.
        mileage =  request.data.get('mileage')
        if mileage:
            serializer = CommentDonateSerializer(data=request.data)
        else:
            serializer = CommentSerializer(data=request.data)
        if serializer.is_valid(raise_exception=True):
            serializer.save(user=request.user, article=article)
            return Response(serializer.data, status=status.HTTP_201_CREATED)
            
    # 댓글 삭제, 수정 (작성자인지 아닌지 체크)
    comment = get_object_or_404(Comment, pk=request.data.get('commentId'))
    if request.user == comment.user or request.user == article.user:
        # 댓글 제거
        if request.method == 'DELETE':
            comment.delete()
            data = {
                'delete' : '댓글이 삭제되었습니다.'
            }
            return Response(data, status=status.HTTP_204_NO_CONTENT)
        # 댓글 수정
        elif request.method == 'PUT':
            serializer = CommentSerializer(comment, data=request.data)
            if serializer.is_valid(raise_exception=True):
                serializer.save()
                return Response(serializer.data)
    return Response({ 'Unauthorized': '권한이 없습니다.'}, status=status.HTTP_403_FORBIDDEN)


# 해당 평가에 적힌 댓글 다 가져오기
@api_view(['GET'])
# @authentication_classes([JSONWebTokenAuthentication])
# @permission_classes([IsAuthenticated])
@permission_classes([AllowAny])
def comment_list(request, article_pk):
    # 마일리지 > 최신 순으로 출력
    comments = Comment.objects.filter(article__pk=article_pk).order_by('-mileage','-pk')
    serializer = CommentDonateSerializer(comments, many=True)
    return Response(serializer.data)


# 평가 포인트 갱신 (마일리지)
@api_view(['PUT'])
@authentication_classes([JSONWebTokenAuthentication])
@permission_classes([IsAuthenticated])
def point_change(request, article_pk):
    article = get_object_or_404(Article, pk=article_pk)
    points = article.points + request.data.get('mileage')
    data = {
        'points': points
    }
    serializer = ArticlePointSerializer(article, data=data)
    if serializer.is_valid(raise_exception=True):
        serializer.save()
        return Response(serializer.data)


# 좋아요 단일 조회, 생성(추가), 삭제
@api_view(['GET', 'POST', 'DELETE'])
@authentication_classes([JSONWebTokenAuthentication])
@permission_classes([IsAuthenticated])
def likes(request, article_pk):
    if request.method == 'GET':
        likes = Likes.objects.filter(user__pk=request.user.pk, article__pk=article_pk).first()
        serializer = LikesSerializer(likes)
        return Response(serializer.data)
    elif request.method == 'POST':
        article = get_object_or_404(Article, pk=article_pk)

        # 포인트 관련
        points = article.points + 1000
        data = {
            'points': points
        }
        serializer = ArticlePointSerializer(article, data=data)
        if serializer.is_valid(raise_exception=True):
            serializer.save()

        # 저장 및 반환
        movie = Movie.objects.filter(articles__pk=article_pk).first() 
        serializer = LikesSerializer(data=request.data)
        if serializer.is_valid():
            # 판별용, 
            serializer.save(user=request.user, article=article, backdrop_path=movie.backdrop_path, title=movie.title)
            return Response(serializer.data, status=status.HTTP_201_CREATED)

    elif request.method == 'DELETE':
        # 포인트 관련
        article = get_object_or_404(Article, pk=article_pk)
        points = article.points - 1000
        data = {
            'points': points
        }
        serializer = ArticlePointSerializer(article, data=data)
        if serializer.is_valid(raise_exception=True):
            serializer.save()

        # 삭제 및 반환
        likes = Likes.objects.filter(user__pk=request.user.pk, article__pk=article_pk).first()
        likes.delete()
        data = {
            'delete' : '좋아요를 해제하셨습니다.'
        }
        return Response(data, status=status.HTTP_204_NO_CONTENT)

# 좋아요 조회(이 유저가 좋아요 한 모든 평가를 최신순으로 가져오자.)
# 프로필 인증 요청 GET은 인증 필요
@api_view(['GET'])
@authentication_classes([JSONWebTokenAuthentication])
@permission_classes([IsAuthenticated])
def likes_list(request):
    likes = Likes.objects.filter(user__pk=request.user.pk).order_by('-pk')[:12]
    serializer = LikesSerializer(likes, many=True)
    return Response(serializer.data)
