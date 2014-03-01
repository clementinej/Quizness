<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<title>Profile Page</title>
	<link rel="stylesheet" type="text/css" href="global.css" />
	<link rel="stylesheet" type="text/css" href="../css/style_login.css" />
</head>

<style>
#submit {
float:right;

}
</style>

<body>	
	
	<nav>
		<ul id="n" class="clearfix">
			<li><a href="#">Profile</a></li>
		</ul>
	</nav>
	
	<div id="content" class="clearfix">
		<section id="left">
			<div id="userStats" class="clearfix">
				<div class="pic">
					<a href="#"><img width="150" height="150" /></a>
				</div>
				
				<div class="data">
					<h1>Johnny Appleseed</h1>
					<h3>San Francisco, CA</h3>
					<div class="sep"></div>
					<ul class="numbers clearfix">
						<li>Quizes Taken<strong>185</strong></li>
						<li>Highest Score<strong>344</strong></li>
						<li class="nobrdr">Last Quiz Taken<strong>127</strong></li>
					</ul>
				</div>
			</div>
			
			<h1>About Me:</h1>
			<p>I figured we could add this as an extension easily.</p>
		</section>
		
		<section id="right">
			<div class="gcontent">
				<div class="head"><h1>Acheivements</h1></div>
				<div class="boxy">
					<p>Keep going!</p>
					
					<div class="badgeCount">
						<a href="#"><img src="" /></a>
						<a href="#"><img src="" /></a>
						<a href="#"><img src="" /></a>
					</div>
					
					<span><a href="#">See all…</a></span>
				</div>
			</div>
			
			
			<div class="gcontent">
				<div class="head"><h1>Friends List</h1><div class="inputs"><input id="submit" type="submit" value="Let's Be Friends!"></div></div>
				<div class="boxy">
					<p>Your friends - 106 total</p>
					
					<div class="friendslist clearfix">
						<div class="friend">
							<span class="friendly"><a href="#">Jerry K.</a></span>
						</div>
						
						<div class="friend">
							<span class="friendly"><a href="#">Katie F.</a></span>
						</div>
						
						<div class="friend">
							<span class="friendly"><a href="#">Ash K.</a></span>
						</div>
					</div>
					
					<span><a href="#">See all...</a></span>
				</div>
			</div>
		</section>
		
		<!-- Show nothing if no friend requests.
		If friend requests, display them.
		If not your profile, display friend request button. -->

		<section id="left">
			<div class="gcontent">
				<div class="head"><h1>Friend Requests</h1></div>
				<div class="boxy">
					<p>People who wanna be your friends</p>
					
				</div>
			</div>
		</section>
	</div>
</body>
</html>