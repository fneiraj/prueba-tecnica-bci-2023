const http = require('http')
const fs = require('fs')
const path = require('path')

const url = require('url');
const init = () => {
  const routes = JSON.parse(
    fs.readFileSync('mock-routes.json', 'utf8')
  )

  var params = function (req) {
    let q = req.url.split('?'), result = {};
    if (q.length >= 2) {
      q[1].split('&').forEach((item) => {
        try {
          result[item.split('=')[0]] = item.split('=')[1];
        } catch (e) {
          result[item.split('=')[0]] = '';
        }
      })
    }
    return result;
  }

  handleSignIn = (req, res) => {
    let body = '';
    req.on('data', chunk => {
      body += chunk.toString();
    });
    req.on('end', () => {
      const { email, password } = JSON.parse(body)

      let mockfile

      if (email === 'fe.neiraj@gmail.com') {
        mockfile = 'mocks/sign-in-1001.response.json'
      }else {
        mockfile = 'mocks/sign-in-1002.response.json'
      }

      res.end(fs.readFileSync(path.resolve(__dirname, mockfile), 'utf8'))
  })

}

const handleTakeOffer = (req, res) => {
  let body = '';
  req.on('data', chunk => {
    body += chunk.toString();
  });
  req.on('end', () => {
    const { paymean, offerId } = JSON.parse(body)

    content = JSON.parse(fs.readFileSync(path.resolve(__dirname, 'mocks/take-offer.response.json'), 'utf8'))
    content.detail = content.detail?.replace('{offerId}', offerId).replace('{paymean}', paymean)

    res.end(JSON.stringify(content))
})

}

  const requestListener = function (req, res) {
    req.params = params(req);
    console.log('----')
    console.log('Request for ' + req.url + ' received.')

    const mockUrl = url.parse(req.url).pathname.replace('%40', '@')

    console.log('Resolved to ' + mockUrl)

    if (!routes[mockUrl]) {
      res.writeHead(404, { 'Content-Type': 'application/json' })
      res.end(JSON.stringify({ message: 'Not Found' }))
      return
    }

    // Set contentType properly as per need
    // Return response

    if (mockUrl.includes('sign-in')) {
    res.writeHead(200, { 'Content-Type': 'application/json' })
      handleSignIn(req, res)
      return
    }
  

    if (mockUrl.includes('take-offer')) {

      const idx = Math.floor(Math.random() * Math.floor(3))

      if (idx === 2) {
        console.log('Internal Server Error')
          res.writeHead(500, { 'Content-Type': 'application/json' })
          res.end(JSON.stringify({ message: 'Internal Server Error' }))
          return
      }

      res.writeHead(200, { 'Content-Type': 'application/json' })

      handleTakeOffer(req, res)
      return
    }

    res.writeHead(200, { 'Content-Type': 'application/json' })

    const content = fs.readFileSync(path.resolve(__dirname, routes[mockUrl]), 'utf8')

    if (req.params.importance || req.params.urgent) {
      const contentFiltered = {
        offers: JSON.parse(content).offers.filter((c) => {
          const isImportanceAll = req.params.importance ? req.params.importance?.toUpperCase() === 'ALL' : true
          const isImportanceEquals = isImportanceAll || c.importance?.toUpperCase() === req.params.importance?.toUpperCase()
          const isUrgentAll = req.params.urgent ? req.params.urgent?.toUpperCase() === 'ALL' : true
          const isUrgentEquals = isUrgentAll || c.urgent?.toUpperCase() === req.params.urgent?.toUpperCase()

          return isImportanceEquals && isUrgentEquals
        })
      }
      res.end(JSON.stringify(contentFiltered))
    } else {

      res.end(content)
    }
  }

  // pass on the request listener
  const server = http.createServer(requestListener)

  // set port number as per choice
  server.listen(3000)
}

init()
