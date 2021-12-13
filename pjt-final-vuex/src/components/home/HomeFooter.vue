<template>
  <div id="home-footer">
    <footer class="border-top">
      <div class="container px-4 px-lg-5">
          <div class="row gx-4 gx-lg-5 justify-content-center">
              <div class="col-md-10 col-lg-8 col-xl-7">
                  <div>
                    Contact Us
                  </div>                 
                  <ul class="list-inline text-center">
                      
                      <li class="list-inline-item">
                          
                          <a id="github" href="https://github.com/k-min9">
                              Kang-Min-Gu
                              <span class="fa-stack fa-lg">     
                                  <i id="circle" class="fas fa-circle fa-stack-2x"></i>
                                  <i class="fab fa-github fa-stack-1x fa-inverse"></i>
                              </span>
                          </a>
                      </li>
                      
                      <li class="list-inline-item">
                          <a id="github" href="https://github.com/limgeonho"> 
                              <span id="name">Lim-Geon-Ho</span>
                              <span class="fa-stack fa-lg">          
                                  <i id="circle" class="fas fa-circle fa-stack-2x"></i>
                                  <i class="fab fa-github fa-stack-1x fa-inverse"></i>
                              </span>
                          </a>
                      </li>
                  </ul>
                  <div class="small text-center text-muted fst-italic">Copyright &copy; Movie Curators 2021</div>
              </div>
          </div>
      </div>
    </footer>

  </div>
</template>

<script>
export default {
  name: 'HomeFooter',
}
</script>

<style>
#home-footer {
  color: white;
}

#github:link {
  color: black; text-decoration: none;
}

#github:hover { 
  color: grey;
}

#circle {
  color: black;
}

#circle:hover { 
  color: grey;
}

#name {
  color: white; text-decoration: none;
}

</style>