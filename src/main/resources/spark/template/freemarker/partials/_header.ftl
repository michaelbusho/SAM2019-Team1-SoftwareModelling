<header id="header">
    <div class="container">

        <div class="d-flex justify-content-between ">
            <div id="logo">
                <h1><a href="#intro" class="scrollto">SAM 2019</a></h1>
                <!-- Uncomment below if you prefer to use an image logo -->
                <!-- <a href="#intro"><img src="img/logo.png" alt="" title="" /></a>-->
            </div>

            <nav id="nav-menu-container">
                <ul class="nav-menu " style="padding-top: 7px;">
                    <li class="menu-active"><a href="/">Profile</a></li>
                    <#if user.getType() == "PCM">
                             <li><a href="/pcmReview">Review Papers</a></li>
                    </#if>

                    <#if user.getType() == "PCC">
                     <li><a href="/pccReview">Review completed Papers</a></li>
                    </#if>

                    <!--     <li><a href="">Login</a> </li> -->



                    <li><a href="/signOut">Sign out</a></li>

                    <!--   <li class="menu-has-children"><a href="">Drop Down</a>
                           <ul>
                               <li><a href="#">Drop Down 1</a></li>
                               <li><a href="#">Drop Down 3</a></li>
                               <li><a href="#">Drop Down 4</a></li>
                               <li><a href="#">Drop Down 5</a></li>
                           </ul>
                       </li> -->
                </ul>
            </nav><!-- #nav-menu-container -->

        </div>

    </div>
</header><!-- #header -->