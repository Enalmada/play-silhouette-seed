import com.mohiva.play.silhouette.api.Silhouette
import javax.inject._
import play.api._
import play.api.http.DefaultHttpErrorHandler
import play.api.i18n._
import play.api.mvc.Results._
import play.api.mvc._
import play.api.routing.Router
import utils.auth.DefaultEnv

import scala.concurrent._

@Singleton
class ErrorHandler @Inject() (implicit messagesApi: MessagesApi, silhouette: Provider[Silhouette[DefaultEnv]], ec: ExecutionContext,
  env: play.api.Environment,
  config: play.api.Configuration,
  sourceMapper: OptionalSourceMapper,
  router: Provider[Router]) extends DefaultHttpErrorHandler(env, config, sourceMapper, router) {

  // I would like to put some more stuff in my devError template but I can't seem to get it to work!
  override def onDevServerError(request: RequestHeader, exception: UsefulException): Future[Result] = {

    // What is the right way to get playEditor in extended ErrorHandler?
    //Future.successful(InternalServerError(views.html.defaultpages.devError(playEditor, exception)))

    // Why is this not showing up?
    Future.successful(InternalServerError("onDevServerError override"))
  }

  // This actually seems to work in production so what is up with onDevServerError?
  override def onProdServerError(request: RequestHeader, exception: UsefulException): Future[Result] =
    Future.successful(InternalServerError(views.html.defaultpages.error(exception)))

}