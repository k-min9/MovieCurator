import qs from 'qs'

const CLIENT_ID = process.env.VUE_APP_GOOGLE_CLIENT_ID
//const CLIENT_ID = ''
const ROOT_URL = 'https://accounts.google.com/o/oauth2/v2/auth'

export default {
  login() {
    const queryStr = qs.stringify({
      client_id: CLIENT_ID,
      // redirect_uri: window.location.href,
      redirect_uri: 'http://localhost:8080/oauth2/google/callback',
      response_type: 'token',
      //scope: 'https://www.googleapis.com/auth/contacts.readonly',
      scope: 'https://www.googleapis.com/auth/userinfo.email'
    })
    const fullUrl = ROOT_URL + "?" + queryStr;
    window.location.href = fullUrl
  },
}