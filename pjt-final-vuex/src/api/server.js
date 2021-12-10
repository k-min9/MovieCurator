export default {
  //URL: 'https://moviecurators.herokuapp.com',
  //URL:'http://127.0.0.1:8000',  // drf
  URL: 'http://127.0.0.1:8080',  // spring
  ROUTES: {
      accounts: {
        // 회원 가임 등등 제어
        default: '/accounts/',
        //get_user_info: '/accounts/<str:username>/get_user_info',
        getUserInfo: '/get_user_info/',  // 뒤에 / 안붙인게 엄청난 차이임. 붙였음!!!! 장고도 수정했음!!!!!!!
        login: '/accounts/api-token-auth/',
        signup: '/accounts/signup/',
        myProfileDetail: '/accounts/profile/',
        profileDetail: '/accounts/profile/', //뒤에 <int:user_pk>

        // 마일리지 충전, 후원받기
        mileageChange: '/accounts/mileage/',
        donate: '/accounts/donate/', // 뒤에 <int:user_pk>
    
        // 큐레이터 이름으로 검색
        curatorSearch: '/accounts/search/',
        // 큐레이터 번호로 단일 상세
        curatorDetail: '/accounts/curators/', // 뒤에 <int:user_pk>/
        // 후원한 큐레이터 리스트 받기
        likesListCurator: '/accounts/curators/likes/',
      },
      movies: {
        // 단일 영화, 추천, 홈
        home: '/movies/',
        //movieDetail: '/movies/<int:movie_pk>/',
        movieDetail: '/movies/',
        //movie_recommend: '/movies/<int:movie_pk>/recommend/',
        movieRecommend: '/recommend/',
    
        // 영화 검색, 분류 관련
        movieSearch: '/movies/search/',
        movieList: '/movies/list/',
    
        // 평가 관련
        //article_detail: '/movies/<int:movie_pk>/articles/',  // 평가 상세
        articleDetail: '/articles/',
        //article_list: '/movies/<int:movie_pk>/articles/list/',  // 그 영화 관련 평가 전부
        articleList: '/articles/list/',
        //article_curator: '/movies/<int:user_pk>/articles/curators/',  // 해당 작성자가 적은 평가 3개
        articleCurator: '/articles/curators/',
        //article_curator_all: '/movies/<int:user_pk>/articles/curators/all/',  // 해당 작성자가 적은 평가 전부, 나중에 페이지네이터, 인피니티 스크롤 등을 생각하여 분리
        articleCuratorAll: '/articles/curators/all/',
        articleHome: '/movies/articles/home/',  // 홈에서 보여줄 상위 평가
        //get_article: '/movies/<int:article_pk>/article/',
        getArticle: '/article/',
    
    
        // 댓글 관련
        //commentDetail: '/movies/<int:article_pk>/comments/',
        commentDetail: '/comments/',
        //commentList: '/movies/<int:article_pk>/comments/list/',
        commentList: '/comments/list/',
    
        // 평가 포인트 갱신 (마일리지)
        //pointChange: '/movies/donate/<int:article_pk>/',
        pointChange: '/movies/donate/',
    
        // 좋아요 관련
        //likes: '/movies/<int:article_pk>/likes/',
        likes: '/likes/',
        likesList: '/movies/likes/',
      },
  },
}