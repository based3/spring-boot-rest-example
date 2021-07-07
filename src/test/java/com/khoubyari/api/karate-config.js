function fn() {
  var env = karate.env; // get system property 'karate.env'
  karate.log('karate.env system property was:', env);
  if (!env) {
    env = 'dev';
  }
  // env: local
  var config = {
    env: env,
   urlBase: 'http://127.0.0.1',
   urlBaseApi: 'http://127.0.0.1',
   login: '',
   password: ''
  }
  if (env == 'dev') {
    // customize
    // e.g. config.foo = 'bar';
  } else if (env == 'e2e') {
    urlBase: 'http://'
    urlBaseApi: 'http://'
  }
  config.faker = Java.type('com.github.javafaker.Faker');
  config.faker_number = Java.type('com.github.javafaker.Number');'
  return config;
}
