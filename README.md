# CalorieTrackerJava
Repo to store the source files for the calorie tracker for the Java levelup

<h2>Business requirements:</h2>
<ol>
  <li>As the user I want to be able to input my <b>height, age, weight and activity level</b> to get an estimate of my BMR</li>
  <li>As the user I want to input the <b>food</b> I eat and the <b>calories/Kilojoules</b> of the respective food, on a specific <b>date</b> </li>
  <li>As the user I want to <b>view my total calories</b> for a <b>specific day</b></li>
  <li>As the user I want to see my current <b>calorie allowance</b> for the day</li>
  <li>As the user I want to be able to change the unit between calorie and Kj</li>
  <li>As the user I want to know if I will <b>gain or lose weight</b> at the <b>end</b> of each days entries</li>
</ol>

<h2>API calls available and their params</h2>

<ol>
  <li>
    Get days calorie intake
    <ul>
      <li>username: Approprpiate user for that day, date: requested date for total calories</li>
      <li>method: GET</li>
      <li>URI: '{dev-path}/<b>{username}</b>/<b>{date}</b>/total'</li>
      </ul>
  </li>
  <li>
    Change units
    <ul>
      <li>units: either Kj or cal</li>
      <li>method: PUT</li>
      <li>body: {
        unit: {unit}
      }</li>
      <li>URI: '{dev-path}/<b>{username}</b>/units'</li>
     </ul>
  </li>
  <li>
    Get current day's  allowance and if user will gain or lose.
    <ul>
      <li>username: the specific user's name</li>
      <li>method: GET</li>
      <li>URI: '{dev-path}/<b>{username}</b>/allowance'</li>
     </ul>
  </li>
  <li>
    Get BMR.
    <ul>
      <li>username: the specific user's name</li>
      <li>method: GET</li>
      <li>URI: '{dev-path}/<b>{username}</b>/bmr'</li>
    </ul>
  </li>
  <li>
    Add item
    <ul>
      <li>name: Items name, calorie: the calorie associated with the item</li>
      <li>method: POST</li>
      <li>body: {
        name: {name},
        calorie: {calorie}
      }</li>
      <li>URI: '{dev-path}/<b>{username}</b>/upload/item'</li>
     </ul>
  </li>
  <li>
    Add user
    <ul>
      <li>username: user's name, weight: user's weight, height: user's height, activity: user's activitiy level,</li>
      <li>method: POST</li>
      <li>body: {
        username: {username},
        weight: {weight},
        height: {height},
        activity: {activity}
      }</li>
      <li>URI: '{dev-path}/<b>{username}</b>/upload/user'</li>
     </ul>
  </li>
  

</ol>
