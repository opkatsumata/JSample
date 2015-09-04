package controllers

import play.api.mvc._

/**
 * Created by y-katsumata on 2015/09/04.
 */
class JSampleController extends Controller {

  def sample1 = Action {
    Ok(views.html.index("Sample Controller#sample1"))
  }

}
