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
    Get days calorie intake based on a specific day for a user
    <ul>
      <li>method: GET</li>
      <li>RequestParam: {
        entryData
      }</li>
      <li>URI: '{dev-path}/getDayCalories/{id}/total'</li>
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
      <li>URI: '{dev-path}/<b>{id}</b>/units'</li>
     </ul>
  </li>
  <li>
    Get users allowance
    <ul>
      <li>method: GET</li>
      <li>URI: '{dev-path}/user/allowance/{id}'</li>
     </ul>
  </li>
  <li>
    Get users allowance
    <ul>
      <li>method: GET</li>
      <li>URI: '{dev-path}/user/bmr/{id}'</li>
     </ul>
  </li>
  
  <li>
    update users bmr
    <ul>
      <li>method: PUT</li>
      <li>RequestParam: {bmr:{new bmr}}      </li>
      <li>URI: '{dev-path}/user/bmr/{id}/update'</li>
     </ul>
  </li>
  <li>
    Change users goal to lose weight
    <ul>
      <li>method: PUT</li>
      <li>URI: '{dev-path}/user/allowance/{id}/lose_weight'</li>
     </ul>
  </li>
  <li>
    Change users goal to gain weight
    <ul>
      <li>method: PUT</li>
      <li>URI: '{dev-path}/user/allowance/{id}/gain'</li>
     </ul>
  </li><li>
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
    Add food entry
    <ul>
      <li>method: POST</li>
      <li>body: {
        name: {name},
        calories: {calorie}
        user_id: {id}
        entryDate: {date}
      }</li>
      <li>URI: '{dev-path}/<b>{id}</b>/upload/foodEntry'</li>
     </ul>
  </li>
  
  <li>
    Update food entry
    <ul>
      <li>method: PUT</li>
      <li>body: {
        name: {name},
        calories: {calorie}
        user_id: {id}
        entryDate: {date}
      }</li>
      <li>URI: '{dev-path}/update/foodEntry/{id}'</li>
     </ul>
  </li>
  
  <li>
    remove food entry
    <ul>
      <li>method: DELETE</li>
      <li>URI: '{dev-path}/remove/foodEntry/{id}'</li>
     </ul>
  </li>
  <li>
    Add user
    <ul>
      <li>method: POST</li>
      <li>body: {
        username: {username},
        firstName: {firstName},
        lastName: {lastName},
        bmr: {bmr},
        allowance: {allowance},
        preferedUnit: {preferedUnit},
        weight: {weight},
        goal: {goal}
      }</li>
      <li>URI: '{dev-path}/upload/user'</li>
     </ul>
  </li>
  <li>
    update user
    <ul>
      <li>method: PUT</li>
      <li>body: {
        username: {username},
        firstName: {firstName},
        lastName: {lastName},
        bmr: {bmr},
        allowance: {allowance},
        preferedUnit: {preferedUnit},
        weight: {weight},
        goal: {goal}
      }</li>
      <li>URI: '{dev-path}/update/user/{id}'</li>
     </ul>
  </li>
  
  <li>
    delete user
    <ul>
      <li>method: DELETE</li>
      <li>URI: '{dev-path}/remove/user/{id}'</li>
     </ul>
  </li>
  
   <li>
    Get user by ID
    <ul>
      <li>id: user's id</li>
      <li>method: GET</li>
      <li>URI: '{dev-path}/getUser/{id}'</li>
     </ul>
  </li>
  <li>
    Get all users
    <ul>
      <li>method: GET</li>
      <li>URI: '{dev-path}/getAllUsers'</li>
     </ul>
  </li>
  <li>
    Get all food entries
    <ul>
      <li>method: GET</li>
      <li>URI: '{dev-path}/getAllEntries'</li>
     </ul>
  </li>
  <li>
    Get food entry by ID
    <ul>
      <li>method: GET</li>
      <li>URI: '{dev-path}/getEntry/{id}'</li>
     </ul>
  </li>
  
  

</ol>
