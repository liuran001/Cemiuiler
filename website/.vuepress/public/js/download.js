var times = String(Math.round(new Date() / 1000));
var key = btoa(times.slice(0, -2) + navigator.userAgent);
document.getElementById("key").value = key;